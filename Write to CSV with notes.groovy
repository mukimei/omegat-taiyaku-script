// :name = Write to CSV with notes :description=Exports the current file to CSV taiyaku with notes
// Copyright 2024, mukimei https://github.com/mukimei
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

import org.omegat.core.data.*
import java.awt.Desktop

// Set folder path and file names
def projectFolder = project.projectProperties.projectRoot
def projectName = project.projectProperties.projectName // Get project name
def tmxFile = new File(projectFolder, "omegat/project_save.tmx") // Updated path
def copyTmxFile = new File(projectFolder, "script_output/project_save_copy.tmx")

// Use project name + 対訳 for output CSV file *Change to whatever you want if not translating between Japanese and English
def csvFile = new File(projectFolder, "script_output/${projectName}_対訳.csv")

// Create output folder if it doesn't exist
def outputFolder = new File(projectFolder, "script_output")
if (!outputFolder.exists()) {
    outputFolder.mkdir()
}

// Initialize CSV content with headers
def csvContents = new StringWriter()
csvContents << """\
Source Text,Target Text,Notes
"""

// Always copy project_save.tmx to project_save_copy.tmx to refresh data
if (tmxFile.exists()) {
    copyTmxFile.withWriter('UTF-8') { writer ->
        writer.write(tmxFile.text)  // Overwrite with the latest content
    }
    println("Refreshed project_save_copy.tmx from project_save.tmx")
} else {
    println("Error: project_save.tmx not found in the omegat folder.")
    return
}

// Read the TMX content
def tmxText = copyTmxFile.text.replaceAll(/<!DOCTYPE[^>]*>/, "")
def tmxContent = new XmlSlurper().parseText(tmxText)

// Create a map to store notes by source text for quick lookup
def notesMap = [:]
tmxContent.body.tu.each { tu ->
    def sourceText = tu.tuv.find { it.@lang == project.projectProperties.sourceLanguage }?.seg.text() ?: ""
    def targetText = tu.tuv.find { it.@lang == project.projectProperties.targetLanguage }?.seg.text() ?: ""
    def notes = tu.note?.text()?.replaceAll(/\r?\n/, " ")?.trim() ?: ""
    notesMap[sourceText] = notes
}

// Helper function to escape CSV fields
def escapeCsvField(field) {
    if (field.contains(",") || field.contains("\n") || field.contains("\"")) {
        return "\"" + field.replaceAll("\"", "\"\"") + "\""
    }
    return field
}

// Iterate through OmegaT project files to extract source and target texts
project.projectFiles.each { fileInfo ->
    fileInfo.entries.each { ste ->
        def source = escapeCsvField(ste.getSrcText().replaceAll("\n", " "))
        def target = escapeCsvField((project.getTranslationInfo(ste)?.translation ?: "").replaceAll("\n", " "))
        def notes = escapeCsvField(notesMap.get(source, ""))

        // Write the row to CSV
        csvContents << """\
$source,$target,$notes
"""
    }
}

// Write to CSV file with UTF-8 BOM
def bom = "\uFEFF"
csvFile.write(bom + csvContents.toString().trim(), 'UTF-8')

// Notify completion
def countWritten = csvContents.toString().split('\n').size() - 1
println("${countWritten} segments written to ${csvFile.absolutePath}")

// Optional: Automatically open the file or folder
def autoopen = "none"
if (autoopen in ["folder", "table"]) {
    Desktop.getDesktop().open(autoopen == "folder" ? csvFile.parentFile : csvFile)
}
