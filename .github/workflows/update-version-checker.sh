#!/bin/sh
# this will update version checker version for Anex Weapons
// get version info from "mod_info.json" file from the string "    "version": { "major": "0", "minor": "2", "patch": "9h" }," and save it to seprate variables VC_major, VC_minor and VC_patch
VC_major=$(grep -oP '(?<="major": ")[^"]*' mod_info.json)
VC_minor=$(grep -oP '(?<="minor": ")[^"]*' mod_info.json)
VC_patch=$(grep -oP '(?<="patch": ")[^"]*' mod_info.json)
// print the version info
echo "Version Checker version of Anex Weapons is: major=$VC_major, minor=$VC_minor, patch=$VC_patch"