# 2048 [![Build Status](https://travis-ci.org/MoriTanosuke/2048.svg?branch=master)](https://travis-ci.org/MoriTanosuke/2048)

First run for a simple ASCII 2048 game.

# Run this game

Open a command line in submodule *twothousand-core* and execute

    mvn compile exec:java

## Open issues

* Random tiles are added anywhere, should be only at the field border
* Random tiles are added even if the field couldn't be moved
* Random tiles are added *before* moving :question:
* ~~There is no score yet~~
* ~~Game has no winning state :laughing:~~ Winning state is displayed with score.
* Score is not calculated correctly. Currently it's only the sum of all tiles. Should be calculated by adding the value of new merged tiles only.
