Global map=LoadAnimMesh("map2\map2.b3d")

Global level=LoadAnimMesh("map2\lvl.b3d")
EntityPickMode level, 2
EntityType level,TypeWall
RecurseSeek(map)

Global sky=Load_SkyBox("sky2\sky")
ScaleEntity sky,100,100,100
EntityOrder sky,1
EntityFX sky,9
;~IDEal Editor Parameters:
;~C#Blitz3D