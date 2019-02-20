# MMCCRanks

MMCCRanks allows a simple way to display user ranks, and allows custom colored prefixes, suffixes, and displays of player usernames.

It is made for Forge 1.7.10 which doesn't have any Forge alternatives to do this. However, MMCCRanks doesn't have any permissions system, as it simply displays and stores player ranks.  

## Configuration

In order to add ranks, use this format in `config/mmccranks.cfg`:

`rankname=rank_display,prefix,suffix,display_color,username_color,prefix_color,suffix_color`

All colors use their name as seen on [this page](https://minecraft.gamepedia.com/Formatting_codes#Color_codes).

For example, this line:

`member=[Member], <,> ,dark_green,white,gray,white`

would produce this result, after applying the rank to a player:

![Image of example rank](https://fs.ikl.sh/selif/twf954tb.png)

In order to make a rank apply to player without a rank, simply change the name of the rank (in the previous example `member`) to `default`. If you do not provide a `default` rank in your config, it will be the default Minecraft chat formatting but with a blue username.

## Usage

In order to add a rank to a player, use the command:

`/rank set [player] [rank]`

For the previous example:

`/rank set 132ikl member`

You can not set a player to the default rank manually. To properly remove a player's rank, use the command:

`/rank unset [player]`
