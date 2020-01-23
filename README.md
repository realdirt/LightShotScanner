LightShotScanner

# !!Attention!!
This program can cause your IP address to be banned by lightshot

# Description
This tool can be used to get some random working links/screenshots from lightshot.

# Disclaimer
This program "pings" the Lightshot web server which can cause the server to block your ip address
Use at your own risk!
if you are using the example below evering should be fine.

# Usages
## Exampels
Use this to download 6 random screenshots the links will be saved in links.txt and the images will be downloaded in the images folder.
`java -Xmx2G -jar LightShotScanner.jar -rnd -save -download`
## Arguments
* -help, -?     | Prints the arguments in the command links.
* -random, -rnd | Use random link generation otherwise links will be generation from a to z.
* -save         | Saves all links by default in links.txt.
* -download     | Downloads all found images by default in the folder Images.
* -count <number> | How many random images should be generated only with -rnd.
* -output <path>, -output <path> | The path where the links should be saved only with -save
* -imageoutput <path>, -imgout <path> | The path where the images should be saved only with -download
* -start <6 characters> | The start characters for generation from a to z for example aaaaaa should not be lower as stop
* -stop <6 characters> | The start characters for generation from a to z for example zzzzzz should not be higher as start

# Other Informtions
Currently only the random link generator is working correctly (-rnd). The other mode can be used but does not take all letters into account.