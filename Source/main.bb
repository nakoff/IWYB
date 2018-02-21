
Const TypePlayer = 1, TypeWall = 2, TypeTrigger = 6, TypeMesh = 4
Const TypeBullet = 3, TypeMonstr = 1
Const TypeEnergy = 5, typeO2 = 7, TypeBox = 8


Graphics3D 0,0,0,0
SetBuffer BackBuffer() 
Global GW 			= GraphicsWidth(): Global GH = GraphicsHeight()
.start
Global Arial		= LoadFont("Arial",20,False,False,False) 
Global Arial2		= LoadFont("Arial2",50,True,False,False)
Global Arial3		= LoadFont("Arial3",35,True,True,False)
Global Arial4		= LoadFont("Arial4",28,True,False,False)
SetFont Arial 
;============= logo =====================================
Global logos 		= LoadImage("sprite\logo.png")
Global logos2 		= LoadImage("sprite\logo2.png")
If GW>1366
	DrawImage 		(logos,(GW/2)-400,(GH/2)-300)
Else
	DrawImage 		(logos2,(GW/2)-400,(GH/2)-250)
End If
Flip
;========================================================

Global restart 				= 0
Global start   				= 0
Global statusmenu 			= True
Global statusgame 			= 0
Global statuscredits 		= 0

Global fon			 		= LoadImage("sprite\menu.jpg")
Global menuimage800  		= LoadImage("sprite\menu1.png")  
Global menuimage1024 		= LoadImage("sprite\menu2.png")
Global menuimage1366 		= LoadImage("sprite\menu3.png")
Global menuimage1680 		= LoadImage("sprite\menu4.png")
Global author800  	 		= LoadImage("sprite\about800.png") 
Global author		 		= LoadImage("sprite\about.png")

Global tex_gameover	 		= LoadImage("sprite\gameover.png")
Global speedtex		 		= LoadImage("sprite\speed.png")
ScaleImage speedtex,0.1,0.1

Global MouseSpeedFactor# 	= 0.4
Global colbull 				= 0
Global staterun				= 0
Global zomby_state			= 2
Global alpha 				= 1.0
Global numzomby 			= 0
Global dead_zombie 			= 0
Global zomby_spawn			= True
Global Spawn 				= 1
Global O2# 					= 100
Global gameover 			= False
Global score%				= 0
Global money# 				= 10
Global scorebonus			= 1
Global speedtime 			= 0
Global speedbonus 			= 0	
Global movee = 1

;------------- ammo  -----------------------
Global ammo 			= 1
Global mag_drobovik 	= 0
Global mag_colt 		= 2
Global patron_colt 		= 12
Global patron_drobovik 	= 0
Global firesc 			= LoadSound("meshes\colt45\fire.ogg")
Global firesd			= LoadSound("meshes\mosin\fire.ogg")
Global nofiresc 		= LoadSound("meshes\colt45\take.ogg")
Global reloadsc 		= LoadSound("meshes\colt45\mag.ogg")
Global drobovik 		= LoadAnimMesh("meshes\mosin\HUD.b3d")
Global colt 			= LoadAnimMesh("meshes\colt45\HUD.b3d")
HideEntity colt: 		HideEntity drobovik

;------------- zomby -----------------------------------------
Global zombi_tex1		= LoadTexture("meshes\1\germanzombie1_D2.tga")
Global zombi_tex2		= LoadTexture("meshes\1\germanzombie1_D2.png")
Global zombi_tex3		= LoadTexture("meshes\1\germanzombie1_D3.jpg")
Global zombi_tex4		= LoadTexture("meshes\1\germanzombie1_D2.tga")
Global zombi_tex5		= LoadTexture("meshes\2\germanzombie2_D2.jpg")
Global zombi_tex6		= LoadTexture("meshes\3\germanzombie4_D2.jpg")
Global zombi	 		= LoadAnimMesh("meshes\1\zombie1.b3d")
Global zombi2	 		= LoadAnimMesh("meshes\2\22.b3d")
Global zombi3	 		= LoadAnimMesh("meshes\3\33.b3d")
PositionEntity 			zombi,500,500,0
HideEntity zombi: 		HideEntity zombi2: HideEntity zombi3

;---------------------- colons (кишки)-----------------------------------------------------------------
Global klns1	 		= LoadMesh("meshes\colon\1.b3d"): Global klns2 = LoadMesh("meshes\colon\2.b3d",klns1)
Global klns3	 		= LoadMesh("meshes\colon\3.b3d",klns1): Global klns4 = LoadMesh("meshes\colon\4.b3d",klns1)
Global klns5	 		= LoadMesh("meshes\colon\5.b3d",klns1)
PositionEntity 			klns1,500,10000,0
HideEntity klns1: 		HideEntity klns2: HideEntity klns3
HideEntity klns4: 		HideEntity klns5

Global menu_state	 	= 1
Global start_ico	 	= LoadImage("sprite\start.png")
Global restart_ico	 	= LoadImage("sprite\restart.png")
Global restart1_ico	 	= LoadImage("sprite\restart1.png")
Global credits_ico	 	= LoadImage("sprite\credits.png")
Global exit_ico		 	= LoadImage("sprite\exit.png")
Global start1_ico	 	= LoadImage("sprite\start1.png")
Global credits1_ico	 	= LoadImage("sprite\credits1.png")
Global exit1_ico	 	= LoadImage("sprite\exit1.png")
Global klick		 	= LoadSound("sound\klick.ogg")
Global back1_ico	 	= LoadImage("sprite\back1.png")

Global blood 			=  LoadAnimImage("sprite\blood.png",330,384,0,5)
Global player_uron 		= 0
Global timer 			= 0
Global light 			= CreateLight()

;--------------- TRIGGER -----------
Global cube1 			= CreateCube()
ScaleEntity 			cube1,700,10,700
EntityType 				cube1,TypeBox
PositionEntity 			cube1,0,-9.5,0
EntityAlpha  			cube1,0.01

Global uroven# 			= 3
Global turn 

;============== PLAYER =================
Include "bin\player.bb"
;============== TYPS ===================
Include "bin\typs.bb"
;============== MAPS ===================
Include "bin\map.bb"
;============ COLLISIONS ===============
Include "bin\collision.bb"

;	##################################################
;	##					MENU						##
;	##################################################
.menu
Global muz1 = LoadSound("sound\muz1.ogg")

If restart = 0 Then
	PlaySound (muz1)
	While statusmenu = True
		Select menu_state
			Case 1
				If KeyHit(28)
					Goto game
				End If
			Case 2
				If KeyHit(28) And start = 1
					FreeResurs()
					Goto start
				End If
			Case 3
				If KeyHit(28)
					Credits()
				End If
			Case 4
				If KeyHit(28)
					End
				End If
		End Select
		RenderWorld
		DrawBlock(fon,0,0)
		If GW=>800 And GW<1024 		Then DrawImage 	(menuimage800,(GW/2)-400,(GH/2)-300)
		If GW=>1024 And GW <1366 	Then DrawImage 	(menuimage1024,(GW/2)-512,(GH/2)-358)
		If GW=>1366 And GW <1680	Then DrawImage 	(menuimage1366,(GW/2)-550,(GH/2)-384)
		If GW=>1680 				Then DrawImage (menuimage1680,(GW/2)-640,(GH/2)-450)
		UpdateMenu()
		Flip
	Wend
	End
End If


.game
restart = 0
start = 1
FreeSound muz1
Global muz2 = LoadSound("sound\muz2.ogg")
HidePointer 
PlaySound (muz2)

	;###################################################
	;##					 GAME  						  ##
	;###################################################
While Not KeyHit(1)
	
	movement(player,cam)
	Updatebonus() 
	UpdateColon();кишки 
	NextGun(); смена оружия 
	Spawn(); спавн зомби
	TurnEntity sky, 0,0.02,0 
	PositionEntity sky,EntityX(cam,True),EntityY(cam,True)-100,EntityZ(cam,True) 
			
	RenderWorld  
	UpdateWorld   
	UpdateMonstr() 
	Hud() 
	UpdateDecal() 
	Flip 
Wend   
FreeSound muz2  
Goto menu 
	;####################################################
	

;=============== MAP ====================
Function RecurseSeek(ent)
	;tab=tab+4
	For i=1 To CountChildren(ent)	
		child=GetChild(ent,i)
		name$=Lower(EntityName(child))
		If Instr(name$,"wall")
			EntityType child, TypeWall 
			EntityPickMode child, 2 
		EndIf			
		If Instr(name$,"trigger")
			x# = EntityX(child)
			y# = EntityY(child)
			z# = EntityZ(child)
			HideEntity child
			PositionEntity cube1,x#,y#-10,z#
		EndIf			
		If Instr(name$,"player")
			x# = EntityX(child)
			y# = EntityY(child)
			z# = EntityZ(child)
			PositionEntity player,x,y,z
        EndIf
	Next
	;tab=tab-4
End Function

;================ PLAYER ===============================
Function movement(player,cam)
	If player_life>0
		
		uroven = uroven+0.0005 
		If uroven>20 uroven = 20
			;---------------- camera --------------------------
			TurnEntity player, 0, 0 -MouseXSpeed()*MouseSpeedFactor, 0 
			TurnEntity cam, MouseYSpeed()*MouseSpeedFactor, 0, 0 
			MoveMouse GraphicsWidth()/2, GraphicsHeight()/2 
			RotateEntity cam, EntityPitch#( cam ), 0, 0	 
			TranslateEntity player,0,-3,0 
	
			UpdateAmmo() 
			UpdateBullet() 
			;--------------- movee  -------------------------------	 
			If KeyDown(200) Or KeyDown(17) Then MoveEntity player,  0, 0, 	speedp+speedbonus 	:movee = 2 
			If KeyDown(208) Or KeyDown(31) Then MoveEntity player,  0, 0,  -speedp-speedbonus	:movee = 2  
			If KeyDown(203) Or KeyDown(30) Then MoveEntity player, -3, 0, 	0 					:movee =2 
			If KeyDown(205) Or KeyDown(32) Then MoveEntity player,  3, 0, 	0 					:movee =2 
			
			If speedtime > MilliSecs() 
				speedbonus = 3 
			Else 
				speedbonus = 0 
			End If 
			
			If O2<1 player_life = player_life - 0.1 
				If player_life>100 Then player_life = 100 
				If O2<0 Then O2= 0: If O2>100 Then O2= 100 
				Local die = 0 
			Else 
			
				If gameover = False  
					playerDie() 
					player_life = 0.0 
				End If 
			
			End If 
End Function

;================ MONSTR ================
Function CreateMonstr.monstr(x#,y#,z#)
	m.monstr 		= New monstr
	;---------------- ????? -------------------
	m\piv 			= CreateSphere()
	EntityRadius 	m\piv,10
	PositionEntity 	m\piv,x,y,z
	EntityAlpha 	m\piv,0
	
	m\vid_zombi = Rand(1,5)
	;-------------- ?????? ????? -----------------
	Select m\vid_zombi
		Case 1
			m\mesh 	= CopyEntity(zombi,m\piv)
		Case 2
			m\mesh 	= CopyEntity(zombi,m\piv)
		Case 3
			m\mesh 	= CopyEntity(zombi,m\piv)
		Case 4
			m\mesh 	= CopyEntity(zombi2,m\piv)
		Case 5
			m\mesh 	= CopyEntity(zombi3,m\piv)
	End Select
	
	EntityPickMode 	m\mesh, 2
	Local random 	= Abs(MilliSecs())
	Local texture 	= Rnd(1,6)
	Select texture
		Case 1
			m\tex 	= zombi_tex1
		Case 2
			m\tex 	= zombi_tex2
		Case 3
			m\tex 	= zombi_tex3
		Case 4
			m\tex 	= zombi_tex4
		Case 5
			m\tex 	= zombi_tex5
		Case 6
			m\tex 	= zombi_tex6
	End Select
	EntityTexture 	m\mesh,m\tex
	TurnEntity 		m\mesh,0,180,0,falce
	ScaleEntity 	m\mesh,0.9,0.9,0.9
	PositionEntity 	m\mesh,0,-2,0,False
	
	m\trigg 		= CreateCube(m\mesh)
	ScaleEntity 	m\trigg,20,30,20
	EntityRadius 	m\trigg,15
	EntityAlpha 	m\trigg,0
	EntityType 		m\trigg,TypeEnergy
	
	m\piv2 			= CreatePivot(m\mesh)
	
	;------------ anims -----------------------
	idle 			= ExtractAnimSeq(m\mesh,42,100)
	idle2 			= ExtractAnimSeq(m\mesh,97,165)
	idle3 			= ExtractAnimSeq(m\mesh,165,232)
	idle4 			= ExtractAnimSeq(m\mesh,232,299)
	atack 			= ExtractAnimSeq(m\mesh,300,405)
	atack2 			= ExtractAnimSeq(m\mesh,407,461)
	atack3 			= ExtractAnimSeq(m\mesh,464,538)
	pad 			= ExtractAnimSeq(m\mesh,3709,3779)
	atackpad 		= ExtractAnimSeq(m\mesh,3792,4021)
	run 			= ExtractAnimSeq(m\mesh,7,29)
	run2 			= ExtractAnimSeq(m\mesh,1158,1188)
	run3 			= ExtractAnimSeq(m\mesh,1259,1306)
	aut 			= ExtractAnimSeq(m\mesh,242,297)
	run14 			= ExtractAnimSeq(m\mesh,1362,1383)
	
	m\x = x: m\y = y: m\z = z
	EntityType 		m\mesh,TypeMonstr
	EntityType 		m\piv,TypeMesh
	
	m\life# 		= 4.0
	m\names 		= Rand(1,7)
	m\staterun 		= MilliSecs()+10000
	numzomby 		= numzomby+1
	Return m
End Function 
Function UpdateMonstr()
	For m.monstr = Each monstr
		;-------------- генератор имен --------------
		Local name$ = ""
		Select m\names
			Case 1
				name = "Rudolf"
			Case 2
				name = "Schulz"
			Case 3
				name = "Schmidt"
			Case 4
				name = "Arnold"
			Case 5
				name = "Fuhrer"
			Case 6
				name = "Hitler"
			Case 7
				name = "Miller"
		End Select
		
		Local anim 	= Rand(1,5)
		Local mesh 	= m\mesh
		Local piv 	= m\piv 
		Local life 	= m\life
		
		PositionEntity m\trigg,EntityX(mesh),EntityY(mesh)+30,EntityZ(mesh)
		;----------- ???????? ????? ? ?? ---------
		If EntityDistance(mesh,cam)< 70 
			player_life = player_life - 0.3
			player_uron = MilliSecs()+2000
			
			If timer < MilliSecs() And gameover = False
				CreateDecal()
				PlaySound(splyerdie)
				timer = MilliSecs()+1000
			End If
		End If 
		
		;-------------- ?????????? ??? ??? ????? -------------------------
		PositionEntity m\piv2,EntityX(piv),EntityY(piv)+80,EntityZ(piv)
		CameraProject (cam,EntityX(m\piv2),EntityY(m\piv2),EntityZ(m\piv2))
		If EntityDistance (player,mesh) < 500
			SetFont Arial3
			Text ProjectedX(),ProjectedY(), Str(name)+": "+life+"%"
			SetFont Arial
		End If	
		
		TranslateEntity piv,0,-1,0 
		If (180 - Abs( DeltaYaw(mesh, player)) < 100) And EntityDistance (mesh,player)< 500 And player_life>0 Then m\staterun = MilliSecs()+8000
		
		;-------------- ????????? ????? ------------
		If life=>2 Then ; ???? ???????? ?????? 4 
			If m\vid_zombi = 1
				state = 1 
			ElseIf m\vid_zombi = 2
				state = 11
			ElseIf m\vid_zombi = 3
				state = 111
			ElseIf m\vid_zombi = 4
				state = 1111
			Else
				state = 11111
			End If 
		End If 
		If life >0 And life <2 Then ; ????? ??????
			If m\vid_zombi = 1
				state = 2  
			ElseIf m\vid_zombi = 2
				state = 22
			ElseIf m\vid_zombi =3
				state = 222
			ElseIf m\vid_zombi =4
				state =22
			Else
				state = 2
			End If 
			
		End If 
		
		If m\staterun >MilliSecs() Then zomby_state = 1
		If m\staterun <MilliSecs() Then zomby_state = 2	
		If stateattack >MilliSecs() Then zomby_state = 3
		
		
		Local rndm = Rand(0,50)
		Local turn_zomby = 0
		If rndm = 20 Then turn = Rand(-180,180)
		If EntityCollided(m\trigg,TypeWall) Or EntityCollided(m\trigg,TypeEnergy)
			turn_zomby = MilliSecs()+1300
			m\staterun = MilliSecs()+5000 
		End If
		Select state
				
				;============================ ?????? ????????? ==================================
				Case 1 
					Select zomby_state 
						;-------------------- ????????? ????????????? ---------------
						Case 1 
							If AnimSeq(mesh)<>10  
								Animate mesh,1,1,10 
							End If 
							If turn_zomby <MilliSecs()
								MoveEntity piv,0,0,4 
								TurnEntity piv,0,Sgn(DeltaYaw(piv,player))*3.5,0
							Else
								If rndm = 20
									TurnEntity piv,0,turn,0
								End If
								MoveEntity piv,0,0,4 
							End If
						;------------------ ????????? ?????? ------------------------
						Case 2 
							If AnimSeq(mesh)<>anim And Rand(1,100) = 3 
								Animate mesh,1,.1,anim 
							End If 
		
					End Select 
				Case 11 
					Select zomby_state 
						;-------------------- ????????? ????????????? ---------------
						Case 1 
							If AnimSeq(mesh)<>11  
								Animate mesh,1,1,11 
							End If 
							If turn_zomby <MilliSecs()
								MoveEntity piv,0,0,2  
								TurnEntity piv,0,Sgn(DeltaYaw(piv,player))*3.5,0 
							Else
								If rndm = 20
									TurnEntity piv,0,turn,0
								End If
								MoveEntity piv,0,0,4 
							End If
						;------------------ ????????? ?????? ------------------------
						Case 2 
							If AnimSeq(mesh)<>anim And Rand(1,100) = 3 
								Animate mesh,1,.1,anim 
							End If 
							
					End Select 
				Case 111
					Select zomby_state 
						;-------------------- ????????? ????????????? ---------------
						Case 1 
							If AnimSeq(mesh)<>12  
								Animate mesh,1,1,12 
							End If
							If turn_zomby <MilliSecs()
								MoveEntity piv,0,0,2  
								TurnEntity piv,0,Sgn(DeltaYaw(piv,player))*3.5,0
							Else
								If rndm = 20
									TurnEntity piv,0,turn,0
								End If
								MoveEntity piv,0,0,4 
							End If
						;------------------ ????????? ?????? ------------------------
						Case 2
							If AnimSeq(mesh)<>anim And Rand(1,100) = 3 
								Animate mesh,1,.1,anim 
							End If 
							
					End Select
				Case 1111
					Select zomby_state 
						;-------------------- ????????? ????????????? ---------------
						Case 1 
							If AnimSeq(mesh)<>14  
								Animate mesh,1,.5,14 
							End If
							If turn_zomby <MilliSecs()
								MoveEntity piv,0,0,1  
								TurnEntity piv,0,Sgn(DeltaYaw(piv,player))*3.5,0 
							Else
								If rndm = 20
									TurnEntity piv,0,turn,0
								End If
								MoveEntity piv,0,0,4 
							End If
						;------------------ ????????? ?????? ------------------------
						Case 2
							If AnimSeq(mesh)<>anim And Rand(1,100) = 3 
								Animate mesh,1,.1,anim 
							End If 
							
					End Select
				Case 11111
					Select zomby_state 
						;-------------------- ????????? ????????????? ---------------
						Case 1 
							If AnimSeq(mesh)<>14 
								Animate mesh,1,.5,14 
							End If
							If turn_zomby <MilliSecs()
								MoveEntity piv,0,0,1  
								TurnEntity piv,0,Sgn(DeltaYaw(piv,player))*3.5,0 
							Else
								If rndm = 20
									TurnEntity piv,0,turn,0
								End If
								MoveEntity piv,0,0,4 
							End If
						;------------------ ????????? ?????? ------------------------
						Case 2
							If AnimSeq(mesh)<>anim And Rand(1,100) = 3 
								Animate mesh,1,.1,anim 
							End If 
							
					End Select	
				;============================= ?????? ????????? ==================================
				Case 2 
					If AnimSeq(mesh)<>9  
						Animate mesh,1,0.5,9 
					End If 
					MoveEntity piv,0,0,0.1 
					TurnEntity piv,0,Sgn(DeltaYaw(piv,player))*1.3,0 
				Case 22 
					If AnimSeq(mesh)<>13  
						Animate mesh,1,0.5,13 
					End If 
					;MoveEntity piv,0,0,0.1 
					;TurnEntity piv,0,Sgn(DeltaYaw(piv,player))*1.3,0
				Case 222 
					If AnimSeq(mesh)<>8  
						Animate mesh,3,0.5,8 
					End If 
					
					;TurnEntity piv,0,Sgn(DeltaYaw(piv,player))*1.3,0
			End Select 
			
			;------------------- ???? ?????? ???? --------------------------
			If EntityCollided (mesh,TypeBullet) Or EntityCollided (piv,TypeBullet) 
				For b.bullet = Each bullet
					m\life= m\life- b\damage
				Next
				m\staterun = MilliSecs()+8000 ; ???? ?????????? ? ?????, ????????? ??? ? ????????? ????? ?? 8 ??? 
			End If
			If m\life<=0 ; ???? ? ????? ????????? ?????  
				Local bonus = Abs(Rand(1,6)) 
				If bonus >4 Then bonus = 2
				;CreateEnergy(EntityX(piv),EntityY(player),EntityZ(piv));??????? ??????? 
				CreateColon(EntityX(piv),EntityY(cam)+10,EntityZ(piv)); ?????
				Createbonus(EntityX(piv),EntityY(cam)+10,EntityZ(piv),bonus)
				FreeEntity mesh 
				FreeEntity m\piv
				numzomby = numzomby - 1
				dead_zombie = dead_zombie + 1
				money = money+1.0
				score = (score + 10)*scorebonus
				Delete m  
			End If
			If m <> Null 
				If m\life <=1
					m\life = m\life - .002
				End If
			End If
		Next  
End Function  

Function Createbonus.bonus(x#,y#,z#,bns)
	b.bonus = New bonus
	b\bns = bns
	Select b\bns
		Case 1
			b\mesh = LoadSprite("sprite\speed.png"); + ????????
		Case 2 
			b\mesh = LoadSprite("sprite\ammocolt.jpg") ; + ???????
		Case 3
			b\mesh = LoadSprite("sprite\life.png") ; + ????????
		Case 4
			b\mesh = LoadSprite("sprite\ammo.png") ; + 
	End Select
			
	EntityAlpha b\mesh,0.9
	PositionEntity b\mesh,x-20,y,z
	ScaleSprite b\mesh,12,12
	EntityType b\mesh,TypeEnergy 
	EntityRadius b\mesh,1,20
	b\life_bonus = MilliSecs() + 10000
End Function
Function Updatebonus()
	For b.bonus = Each bonus
		Local mesh = b\mesh
		TranslateEntity mesh,0,-3,0
		Local dist = EntityDistance (mesh, player)
		If dist <50
			Select b\bns
				Case 1 ;Speed
					speedtime = MilliSecs()+10000
				Case 2
					mag_colt = mag_colt+1 ; Colt
				Case 3
					player_life=player_life+5
					If player_life>100 Then player_life = 100
				Case 4 
					mag_drobovik = mag_drobovik+1 ; Drob
			End Select
			FreeEntity mesh
			Delete b
		Else If b\life_bonus < MilliSecs()
			FreeEntity mesh
			Delete b
		End If 
	Next
End Function

Function Load_SkyBox( file$ )
;+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	m=CreateMesh()
	
	;front face
	
	b=LoadBrush(file +"_Le.png",49 )::
	If Not B b=LoadBrush( file +"_Le.png",49 )::
		s=CreateSurface( m,b )
		AddVertex s,-1,+2,-1,0,0:AddVertex s,+1,+2,-1,1,0
		AddVertex s,+1,-0,-1,1,1:AddVertex s,-1,-0,-1,0,1
		AddTriangle s,0,1,2:AddTriangle s,0,2,3
		FreeBrush b
		
	;right face
		b=LoadBrush( file +"_fr.png",49 )::
		If Not B b=LoadBrush(file +"_fr.png",49 )::
			s=CreateSurface( m,b )
			AddVertex s,+1,+2,-1,0,0:AddVertex s,+1,+2,+1,1,0
			AddVertex s,+1,-0,+1,1,1:AddVertex s,+1,-0,-1,0,1
			AddTriangle s,0,1,2:AddTriangle s,0,2,3
			FreeBrush b
			
	;back face
			b=LoadBrush(file +"_ri.png",49 )::
			If Not B b=LoadBrush(file +"_ri.png",49 )::
				s=CreateSurface( m,b )
				AddVertex s,+1,+2,+1,0,0:AddVertex s,-1,+2,+1,1,0
				AddVertex s,-1,-0,+1,1,1:AddVertex s,+1,-0,+1,0,1
				AddTriangle s,0,1,2:AddTriangle s,0,2,3
				FreeBrush b
	;left face
				b=LoadBrush(file +"_bk.png",49 )::
				If Not B b=LoadBrush(file +"_bk.png",49 )::
					s=CreateSurface( m,b )
					AddVertex s,-1,+2,+1,0,0:AddVertex s,-1,+2,-1,1,0
					AddVertex s,-1,-0,-1,1,1:AddVertex s,-1,-0,+1,0,1
					AddTriangle s,0,1,2:AddTriangle s,0,2,3
					FreeBrush b
	;top face
					b=LoadBrush(file +"_UP.png",49 )::
					If Not B b=LoadBrush(file +"_UP.png",49 )::
						s=CreateSurface( m,b )
						AddVertex s,-1,+2,+1,0,1:AddVertex s,+1,+2,+1,0,0
						AddVertex s,+1,+2,-1,1,0:AddVertex s,-1,+2,-1,1,1
						AddTriangle s,0,1,2:AddTriangle s,0,2,3
						FreeBrush b
						FlipMesh m
						EntityFX m,9
						EntityOrder m,1
						RotateEntity m,0,90,0 
						Return m
End Function

Function Hud()
	Local mag
	Local bullet
	Select ammo
		Case 1
			mag = mag_colt
			bullet = patron_colt
		Case 2 
			mag = mag_drobovik
			bullet = patron_drobovik
	End Select
	Local hard
	If uroven <5 Then hard = 25
	If uroven =>5 And uroven <10 Then hard = 50
	If uroven =>10 And uroven <20 Then hard = 75
	If uroven => 20 Then hard = 100
	;Text GraphicsWidth()-150,400,"HARD = "+hard+"%"
	If speedtime >MilliSecs()
		DrawImage speedtex,GraphicsWidth()-250,GraphicsHeight()-100
	End If
	If gameover = False
		SetFont Arial3
		Color 80,200,120
		Text 100,GraphicsHeight()-50,"HEALTH : "+Int(player_life) + "%"
		Text GraphicsWidth()-250,GraphicsHeight()-50,"Bullet : "+bullet + "/"+mag
		Text GraphicsWidth()-250,150,"HARD = "++hard+"%"
		Text GraphicsWidth()-250,50,"ZOMBIE = "+numzomby
		SetFont Arial4
		
		SetFont Arial
		Color 207,181,59
	End If
	If gameover = True
		SetFont Arial2
		Text (GraphicsWidth()/2)-100,(GraphicsHeight()/2)-150,"S C O R E  = "+Abs(score)
		Text (GraphicsWidth()/2)-100,(GraphicsHeight()/2),"dead zombie = "+dead_zombie
		SetFont Arial
	End If
End Function

Function CreateColon.colon(xx#,yy#,zz#)
	c.colon = New colon
	Local random# = Rand (0,360) 
	c\mesh1 = CopyEntity(klns1):c\mesh2 = CopyEntity(klns2,c\mesh1):c\mesh3 = CopyEntity(klns3,c\mesh1)
	c\mesh4 = CopyEntity(klns4,c\mesh1):c\mesh5 = CopyEntity(klns5,c\mesh1)
	RotateEntity c\mesh1,0,random,0,False:RotateEntity c\mesh2,0,random,0,False:RotateEntity c\mesh3,0,random,0,False
	RotateEntity c\mesh4,0,random,0,False:RotateEntity c\mesh5,0,random,0,False
	ScaleEntity c\mesh1,1.6,1.6,1.4
	EntityType c\mesh1,TypeTrigger
	PositionEntity c\mesh1,xx,yy,zz
	c\speedcolon# = -3
	c\alpha = .99
End Function
Function UpdateColon()
	For c.colon = Each colon
		TranslateEntity c\mesh1,0,c\speedcolon,0
		c\alpha = c\alpha - 0.005
		If c\alpha <0.0 
			c\speedcolon# = -.05
			EntityType c\mesh1,TypeEnergy
			TranslateEntity c\mesh1,0,c\speedcolon,0
		EndIf
		If c\alpha <-1.0
			FreeEntity c\mesh1
			Delete c
		End If
	Next
End Function

Function playerDie()
	PlaySound(splyerdie)
	PositionEntity cam,0,0,0
	RotateEntity cam,0,10,45
	over = True
	gameover = True
	For a.guns = Each guns
		HideEntity a\mesh
	Next
	For m.monstr = Each monstr
		m\staterun = MilliSecs()-8000
	Next
	
End Function

Function CreateAmmo.guns(ammo)
	a.guns = New guns
			a\mesh = CopyEntity(colt,cam)
			idlc = ExtractAnimSeq (a\mesh,17,64)
			runc = ExtractAnimSeq (a\mesh,66,95)
			firec = ExtractAnimSeq(a\mesh,95,105)
			reloadc = ExtractAnimSeq(a\mesh,154,217)
	PositionEntity a\mesh,0,0,-2,False
	ScaleEntity a\mesh,.1,.1,.1
	RotateEntity a\mesh,0,0,0,False
End Function
Function UpdateAmmo()
	
	For a.guns = Each guns
		Select ammo
			Case 1 ;---------------------------------------------------
				If Animating(a\mesh)=False 
					Animate a\mesh,1,.3,1 ; idle
				End If 
				If MouseHit(1) And AnimSeq (a\mesh)<>4
					If patron_colt>0 Then
						Animate a\mesh,3,1,3
						PlaySound(firesc)
						CreateBullet()
						patron_colt = patron_colt - 1
					Else
						PlaySound (nofiresc)
						movee = 1
					EndIf
					If movee = 2
						If AnimSeq(a\mesh)<>3 And AnimSeq(a\mesh)<>4
							If AnimSeq(a\mesh)<>2
								Animate a\mesh,1,.5,2
							End If
						End If 
					Else
						If AnimSeq(a\mesh)<>3 And AnimSeq(a\mesh)<>4
							If AnimSeq(a\mesh)<>1
								Animate a\mesh,1,.3,1
							End If
						End If
					End If
				EndIf
			Case 2 ;-----------------------------------------------------
				If Animating(a\mesh)=False 
					Animate a\mesh,1,.3,1 ; idle
				End If 
				If MouseHit(1) And AnimSeq (a\mesh)<>4
					If patron_drobovik>0 Then
						Animate a\mesh,3,1,3
						PlaySound(firesd)
						CreateBullet(1,4,200)
						patron_drobovik = patron_drobovik - 1
					Else
						PlaySound (nofiresc)
						movee = 1
					EndIf
					If movee = 2
						If AnimSeq(a\mesh)<>3 And AnimSeq(a\mesh)<>4
							If AnimSeq(a\mesh)<>2
								Animate a\mesh,1,.5,2
							End If
						End If 
					Else
						If AnimSeq(a\mesh)<>3 And AnimSeq(a\mesh)<>4
							If AnimSeq(a\mesh)<>1
								Animate a\mesh,1,.3,1
							End If
						End If
					End If
				EndIf
		End Select
	Next 
	If mag_colt >10 Then mag_colt = 10
	If mag_drobovik >5 Then mag_drobovik = 5
	Reload()
End Function
Function Reload()
	For a.guns = Each guns
		Select ammo
			Case 1
				If KeyHit(19) And 	mag_colt>0
					Animate 		a\mesh,3,.5,4
					PlaySound 		(reloadsc)
					patron_colt 	= 12
					mag_colt 		= mag_colt - 1
				End If
			Case 2
				If KeyHit(19) And 	mag_drobovik>0
					Animate 		a\mesh,3,.3,4
					PlaySound 		(reloadsc)
					patron_drobovik = 8
					mag_drobovik 	= mag_drobovik - 1
				End If
		End Select
	Next
End Function
Function NextGun(key=15)
	If KeyHit(key)
		ammo = ammo + 1
		If ammo > 2 Then  ammo = 1
		For a.guns = Each guns
			FreeEntity a\mesh
			Select ammo
				Case 1
					a\mesh 			= CopyEntity(colt,cam)
					idlc 			= ExtractAnimSeq (a\mesh,17,64)
					runc 			= ExtractAnimSeq (a\mesh,66,95)
					firec 			= ExtractAnimSeq(a\mesh,95,105)
					reloadc 		= ExtractAnimSeq(a\mesh,154,217)
					PositionEntity 	a\mesh,0,0,-2,False
				Case 2
					a\mesh 			= CopyEntity(drobovik,cam)
					idlc 			= ExtractAnimSeq (a\mesh,17,64)
					rund 			= ExtractAnimSeq (a\mesh,65,96)
					firec 			= ExtractAnimSeq(a\mesh,95,105)
					reloadd 		= ExtractAnimSeq(a\mesh,270,308)
					PositionEntity 	a\mesh,0,-.8,1.5,False
			End Select
			ScaleEntity 			a\mesh,.1,.1,.1
			RotateEntity 			a\mesh,0,0,0,False
		Next			
	End If
End Function

Function CreateBullet(rad%=1,damage#=1,life#=200)
		b.bullet=New bullet
		b\Model=CreateSphere(10,cam)   ;??? ????? ??????
		EntityAlpha b\model,.1
		ScaleMesh b\Model,.5,.5,.5
		b\speed#=82+Rnd(0,1)	
		EntityType b\Model,TypeBullet
		EntityRadius b\Model,rad	
		PointEntity b\Model,gun
		b\damage = damage
		Collisions TypeBullet,TypeMonstr,2,1
		Collisions TypeBullet,TypeWall,2,1
		Collisions TypeWall,TypeBullet,2,1
		EntityParent b\Model,0
		b\life = MilliSecs()+life
End Function
Function UpdateBullet()
	For b.bullet=Each bullet	
		MoveEntity b\Model,0,0,b\speed#	
		If b\life < MilliSecs() Or EntityCollided(b\Model,TypeWall) Or EntityCollided(b\Model,TypeMesh)
			FreeEntity b\Model
			Delete b
		EndIf
	Next
End Function

Function UpdateMenu()
	Local mx = MouseX(): Local my = MouseY()
	If KeyHit(200)
		menu_state = menu_state - 1
		PlaySound(klick)
	End If
	If KeyHit(208)
		menu_state = menu_state + 1
		PlaySound (klick)
	End If
	If menu_state <1 Then menu_state = 1
	If menu_state >4 Then menu_state = 4		
	Select menu_state 
		Case 1
			DrawImage start1_ico,(GraphicsWidth()/2)-25,(GraphicsHeight()/2)-200
			DrawImage restart_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)-100
			DrawImage credits_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)
			DrawImage exit_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)+100
		Case 2
			DrawImage start_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)-200
			DrawImage restart1_ico,(GraphicsWidth()/2)-25,(GraphicsHeight()/2)-100
			DrawImage credits_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)
			DrawImage exit_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)+100
		Case 3
			DrawImage start_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)-200
			DrawImage restart_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)-100
			DrawImage credits1_ico,(GraphicsWidth()/2)-25,(GraphicsHeight()/2)
			DrawImage exit_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)+100
		Case 4
			DrawImage start_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)-200
			DrawImage restart_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)-100
			DrawImage credits_ico,(GraphicsWidth()/2),(GraphicsHeight()/2)
			DrawImage exit1_ico,(GraphicsWidth()/2)-25,(GraphicsHeight()/2)+100
	End Select
End Function
Function Credits()
	While statuscredits = 0
		If KeyHit(1) Or KeyHit(28)
			statuscredits = 1
		End If
		RenderWorld
		DrawBlock (fon,0,0)
		If GW >1366
			DrawImage author,(GW/2)-657,(GH/2)-353
			hieght = 300
		Else
			DrawImage author800,(GW/2)-400,(GH/2)-215
			hieght = 200
		End If
		DrawImage back1_ico,(GraphicsWidth()/2)-150,(GraphicsHeight()/2)+hieght
		Flip
	Wend
	statuscredits = 0
End Function

Function CreateDecal.decal()
	d.decal = New decal
	d\mesh = blood
	d\random = Rand(-GW/2,GW/2)
	d\random2 = Rand(-GH/2,GH/2)
	d\random3 = Rand(0,5)
	
End Function
Function UpdateDecal()
	For d.decal = Each decal
		If player_uron > MilliSecs()
			DrawImage d\mesh,(GW/2)+d\random,(GH/2)+d\random2,Abs(d\random3)
		Else
			Delete d
		End If
	Next
End Function

Function FreeResurs()
	FreeEntity cam
	FreeEntity player
	FreeSound splyerdie
	FreeSound muz1
	
	For m.monstr = Each monstr
		FreeEntity m\mesh
		FreeEntity m\piv
		Delete m
	Next
	For b.bonus = Each bonus
		FreeEntity b\mesh
		Delete b
	Next
	For a.guns = Each guns
		Delete a
	Next
	For e.energy = Each energy
		FreeEntity e\mesh
		Delete e
	Next
	For bb.bullet = Each bullet
		FreeEntity bb\model
		Delete bb
	Next
	FreeEntity zomby
	FreeEntity zomby2
	FreeEntity zomby3
End Function

Function Spawn()
	If numzomby <1 Then zomby_spawn = True
	If zomby_spawn = True
		For i=1 To CountChildren(map)
			Local randoom = Rand(-50,50)
			childs=GetChild(map,i) 
			names$=Lower(EntityName(childs)) 
			If EntityCollided(player,TypeBox) 
				If Instr(names$,"enemy") 
					x# = EntityX(childs) 
					y# = EntityY(childs)  
					z# = EntityZ(childs) 
					If randoom = 30 
						CreateMonstr(x+randoom,y,z+randoom) 
					End If 
				EndIf 
			End If 
		Next 
		If numzomby =>uroven Then zomby_spawn = False
	End If 
End Function
;~IDEal Editor Parameters:
;~F#114#267#27C#296#2CD#2F5#302#312#321#32C#36E#3AE#3B8#3DB#3EF#3F7#401#420
;~C#Blitz3D