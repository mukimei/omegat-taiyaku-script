This script allows for the user to create a TAIYAKU 対訳 or Bilingual Translation. It should work for any language pair in OmegaT but has only been tested and proven to work with absolute certainty with Japanese as the source text and English as the target text.
If the translator created any notes in the NOTEPAD of OmegaT they will be printed in a third column in the output CSV file.

So
------------------------------------
SOURCE TEXT |  TARGET TEXT | NOTES
------------------------------------

This is useful for making changes to the translation outside of OmegaT! 

The logic is:

1) Takes the source text
2) Takes the TMX file text (with the notes!)
3) Reorders the TMX file text to match the order of the source text (since the TMX file is out of order for some reason)
4) Combines it with the Target text (if any, if there are empty translation the Target section will be blank).


Enjoy!
