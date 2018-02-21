;-				player			  -
;----------------------------------

Global player=CreateSphere();		  
TurnEntity player,0,0,0
EntityRadius player, 10
EntityType player, TypeWall;	
Global player_life# = 1000
;Global ammo = 20
Global magazin = 11
Global speedp# = 5
Global splyerdie = LoadSound("sound\playerdie.wav")

;--------					------|
Global cam=CreateCamera(player) ; |
PositionEntity cam,0,50,0,False
CameraRange cam,1,10000
EntityType cam,TypePlayer
CreateListener(cam)
Global gun = CreatePivot(cam)
PositionEntity gun,.29,.27,10
;----------------------------------
CreateAmmo(ammo)
;~IDEal Editor Parameters:
;~C#Blitz3D