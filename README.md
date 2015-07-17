# 2048 [![Build Status](https://travis-ci.org/MoriTanosuke/2048.svg?branch=master)](https://travis-ci.org/MoriTanosuke/2048)

First run for a simple ASCII 2048 game.

# Run this game

Open a command line and execute

    mvn compile exec:java

## Open issues

* Random tiles are added anywhere, should be only at the field border
* Random tiles are added even if the field couldn't be moved
* Random tiles always add *2*, there should be a 10% chance for a *4*
* Game has no winning state :laughing:
