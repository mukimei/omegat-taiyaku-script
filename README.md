----
OmegaT 対訳 (Bilingual Translation) Script
----

This script generates a 対訳 (TAIYAKU) – Bilingual Translation in CSV format. It works for any language pair in OmegaT, though it has been thoroughly tested with Japanese as the source text and English as the target text.

If the translator included any notes in OmegaT’s Notepad, these will appear in a third column of the CSV output.

----
Features
----

1. Automatically generates a copy of project_save.tmx (as project_save_copy.tmx) each time the script runs.
2. Reorders the content of the TMX file to match the order of the source text (fixing potential misalignments in the original TMX).
3. Saves the output CSV file and the TMX copy in the script_output folder within your OmegaT project.

Location of output CSV file:
{YourProjectName}/script_output

----
CSV format:
----
    Source Text | Target Text | Notes

This allows you to easily edit translations outside of OmegaT.

----
Installation Instructions
----

Place the script in the scripts folder of your OmegaT installation directory.

*Disable read-only mode and ensure you have admin rights to modify the folder.*

{YourDrive}:\OmegaT\scripts

----
How It Works
----
1. Extracts the source text from the OmegaT project files.
2. Copies the TMX file (project_save.tmx to project_save_copy.tmx) with the latest updates, including any notes added by the translator.
3. Reorders the TMX content to match the source text order.
4. Merges source, target, and notes into a CSV file.
5. Saves the CSV file and TMX copy in the script_output folder.

-----
Usage
-----
This script is particularly useful when you want to review or modify translations outside of OmegaT in a more structured format.

Enjoy translating! 😊
