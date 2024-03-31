#!/bin/bash
# this will update version checker version for Anex Weapons

# go to the directory where the mod_info.json file is located
cd ../..
# get version info from "mod_info.json" file from the string "    "version": { "major": "0", "minor": "2", "patch": "9h" }," and save it to separate variables VC_major, VC_minor and VC_patch
VC_major=$(jq -r '.version.major' mod_info.json)
VC_minor=$(jq -r '.version.minor' mod_info.json)
VC_patch=$(jq -r '.version.patch' mod_info.json)
# print the version info
echo "Version Checker version of Anex Weapons is: major='$VC_major', minor='$VC_minor', patch='$VC_patch'"
# set the major, minor, patch in file "Anex_Weapons.version" to the version info from "mod_info.json"
#!/bin/bash

sed -i "s/\"major\":.*/\"major\":$VC_major,/g" Anex_Weapons.version
sed -i "s/\"minor\":.*/\"minor\":$VC_minor,/g" Anex_Weapons.version
sed -i "s/\"patch\":.*/\"patch\":$VC_patch,/g" Anex_Weapons.version

cat Anex_Weapons.version