#!/bin/bash
# PURPOSE: install intellij, document how to uninstall
echo "quick and dirty - undestand this file and then modify as needed"
exit 1

set -x
set -eu
# NOTE: this script is highly unoptimized

mkdir -p intellij && cd intellij

# download gzip tarball and checksum
# source: https://www.jetbrains.com/idea/download/#section=linux
if [[ 1 -eq 1 ]]; then
  if [[ ! -r ideaIC-2019.3.2.tar.gz ]]; then
    curl -L -O https://download.jetbrains.com/idea/ideaIC-2019.3.2.tar.gz
    # output:
    # c38f18a2b2246b9a53fd62d454ccf67996bf59adc0b7e3843be0a9cf44637127 *ideaIC-2019.3.2.tar.gz
    curl -L -O https://download.jetbrains.com/idea/ideaIC-2019.3.2.tar.gz.sha256
  fi
fi
# verify
if [[ 1 -eq 1 ]]; then
  # output:
  # $ sha256sum ideaIC-2019.3.2.tar.gz 
  # c38f18a2b2246b9a53fd62d454ccf67996bf59adc0b7e3843be0a9cf44637127  ideaIC-2019.3.2.tar.gz
  sha256sum -c ideaIC-2019.3.2.tar.gz.sha256
fi

# extractivate
if [[ 1 -eq 1 ]]; then
  if [[ ! -e "idea-IC-193.6015.39" ]]; then
    tar -xzf ideaIC-2019.3.2.tar.gz
  fi
  if [[ ! -d "idea-IC-193.6015.39" ]]; then
    echo "ERROR - not a dir!" && exit 1
  fi
  #mv idea-IC-193.6015.39 ideaIC
fi

# run setup
if [[ 1 -eq 1 ]]; then
  #./intellij/idea-IC-193.6015.39/bin/idea.sh
  ./idea-IC-193.6015.39/bin/idea.sh
fi
exit 0


# UNINSTALL
# remove shortcut
# src: https://www.howtogeek.com/445303/how-to-create-desktop-shortcuts-on-ubuntu/
# rm ~/.local/share/applications/jetbrains-idea-ce.desktop 
# remove install
# rm -rf ~/.IdeaIC*
# remove launcher
# sudo rm /usr/local/bin/idea
