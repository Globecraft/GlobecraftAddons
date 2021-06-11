# GlobecraftAddons

Plugin of miscellaneous addons for the Globecraft server.

## Commands

### gcaddons

Permission: `gcaddons.list`  
Aliases: `gcaddon`  
Usage: `/gcaddons [addon]`

Show a list of all addons or information about specific addon.

### gcenable

Permission: `gcaddons.toggle`  
Usage: `/gcenable <addon>`

Enable an addon.

### gcdisable

Permission: `gcaddons.toggle`  
Usage: `/gcdisable <addon>`

Disable an addon.

### gcreload

Permission: `gcaddons.reload`  
Usage: `/gcreload <addon>`

Reload an addon (quickly disable and enable).

### vday

Permission: `gcaddons.voteskipnight`  
Aliases: `vd` `voteday` `dset` `dvote`  
Usage: `/vday`

Start a night skip vote or add your vote to the pool.

### rtp

Permission: `gcaddons.rtp`  
Aliases: `randomtp`
Usage: `/rtp`

Teleport randomly inside the boundaries of RandomSpawn addon.

## Included add-ons

### CustomRecipes

Some simple custom recipes for the server.  
Because gold is used as an currency, powered rails can be crafted with iron.  
Because squids are hard to find, ink can be crafted with both coals.

### Hypothermia

Damage players in cold areas.  
These areas include places high up in the mountains.  
And places marked with WorldGuard `hypothermia` flag.

A piece of leather armor will mitigate damage by 25%.  
Full set will completely null the damage.  
Other types of armor will reduce damage by 12.5%.   
Full set making up 50%.

### ItemNamedBy

Adds a lore to items named with anvils.  
The lore will tell who named the item.  
This is very usefull for creating currencies, 
signing authentic items or contracts. 

### RandomSpawn

Randomly spawn players in a safe place on the map,  
when they log in the first time. Also randomly  
spawn the player near their place of death if  
they haven't set  up a town or a respawn point anywhere.

### VoteSkipNight

Nights are annoying to skip on multiplayer servers  
because there is always someone refusing to sleep.  
This addon makes it so that only a part of players  
need to vote for the skip and it will happen.
