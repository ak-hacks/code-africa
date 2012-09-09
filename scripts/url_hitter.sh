#####################################
# @author: Anurag Kapur
# @Description: Simple URL Hitter
# Hits url from a list
#####################################

#!/bin/sh

URLSFILE=$1

function usage() {
	echo "
	==========================================
	Usage: url_hitter.sh
	example:
	./url_hitter.sh /tmp/url_list.txt
	==========================================
	"
}

function hitUrl()
{
	wget $1
}

function process()
{
	for url in `cat $URLSFILE`
	do
		hitUrl $url
	done
}

if [ $# -lt 1 ]; 
then
	echo "$0: Error (Invalid number of arguments)"
	usage
else
	process
fi
