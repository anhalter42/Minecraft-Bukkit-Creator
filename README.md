Minecraft-Bukkit-Creator
========================

Minecraft Bukkit Creator

    c_markpos:
      description: set current player position for given marker. 
      usage: /c_markpos <markername> [<sourcemarker>|(<x> <y> <z>)]
      aliases: mp
      permission: c.markpos
      permission-message: You don't have permission
    c_markbox:
      description: set current player position for given marker and second marker calculated with whd. 
      usage: /c_markbox <marker1> <marker2> <width> <height> <depth> 
      aliases: mb
      permission: c.markbox
      permission-message: You don't have permission
    c_area_save:
      description: save area given by marker a and b to given file. 
      usage: /c_area_save <filename> [<index>|add]
      aliases: asave
      permission: c.area_save
      permission-message: You don't have permission
    c_area_load:
      description: load area to player location. 
      usage: /c_area_load <filename> [<index>|play]
      aliases: aload
      permission: c.area_load
      permission-message: You don't have permission
    c_area_delete:
      description: delete area on disk. 
      usage: /c_area_delete <filename>
      permission: c.area_delete
      permission-message: You don't have permission
    c_area_remove:
      description: remove part of area on disk. 
      usage: /c_area_remove <filename> <index>
      permission: c.area_remove
      permission-message: You don't have permission
    c_we_fill:
      description: fill region marker 1 to marker 2 with given material. 
      usage: /c_we_fill empty|<materialname>|<materialid> [<rawmetadata> [<marker1> <marker2>]]
      aliases: fill
      permission: c.we_fill
      permission-message: You don't have permission
    c_we_box:
      description: create walls for region marker 1 to marker 2 with material. 
      usage: /c_we_box <walls_nsewtb> <Materialname>|<Materialid> [<rawmetadata> [<marker1> <marker2>]]
      aliases: box
      permission: c.we_box
      permission-message: You don't have permission
    c_we_markbox:
      description: fill region marker 1 to marker 2 with marker material. 
      usage: /c_we_markbox [<marker1> <marker2> [<Materialname>|<Materialid>]]
      aliases: mbox
      permission: c.we_markbox
      permission-message: You don't have permission
