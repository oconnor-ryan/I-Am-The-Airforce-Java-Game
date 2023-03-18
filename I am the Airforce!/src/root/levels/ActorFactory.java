package root.levels;

import root.actors.seaVehicles.*;
import root.actors.planes.*;
import root.actors.*;
import root.actors.groundVehicles.*;;


public class ActorFactory 
{
	public static Actor getActor(Level l, ActorNames name)
	{
		Actor a;
		switch(name)
		{
			case AAGUN:
				a = new AAGun(l, 0,0);
				break;
			case BOMB_FLINGER:
				a = new BombFlinger(l,0,0);
				break;
			case SAM:
				a = new SAM(l, 0,0);
				break;
			case SMART_AAGUN:
				a = new SmartAAGun(l,0,0);
				break;
			case BOMBER:
				a = new Bomber(l, 0,0);
				break;
			case BOMB_ONLY_BOMBER:
				a = new BombOnlyBomber(l,0,0);
				break;
			case JET:
				a = new Jet(l, 0,0);
				break;
			case MISSLE_JET:
				a = new MissleJet(l,0,0);
				break;
			case SINE_WAVE_BOMBER:
				a = new SineWaveBomber(l, 0,0);
				break;
			case SMART_JET:
				a = new SmartJet(l,0,0);
				break;
			case TARGET_PLANE:
				a = new TargetPlane(l, 0,0);
				break;
			case WAVE_JET:
				a = new WaveJet(l,0,0);
				break;
			case FLAK_GUN:
				a = new FlakGun(l,0,0);
				break;
			case SMART_FLAK_GUN:
				a = new SmartFlakGun(l,0,0);
				break;
			case SHOTGUN_JET:
				a = new ShotGunJet(l, 0, 0);
				break;
			case FLAK_JET:
				a = new FlakJet(l,0,0);
				break;
			case FLAK_SHOTGUN_JET:
				a = new FlakShotGunJet(l,0,0);
				break;
			case SHOTGUN_FLAK_GUN:
				a = new ShotGunFlakGun(l,0,0);
				break;
			case SHOTGUN_AAGUN:
				a = new ShotGunAAGun(l,0,0);
				break;
			case SMART_SHOTGUN_JET:
				a = new SmartShotGunJet(l,0,0);
				break;
			case SMART_SHOTGUN_FLAK_JET:
				a = new SmartShotGunFlakJet(l,0,0);
				break;
			case SMART_FLAK_JET:
				a = new SmartFlakJet(l,0,0);
				break;
			case SMART_SHOTGUN_AAGUN:
				a = new SmartShotGunAAGun(l,0,0);
				break;
			case SMART_SHOTGUN_FLAK_GUN:
				a = new SmartShotGunFlakGun(l,0,0);
				break;
			case PT_BOAT:
				a = new PTBoat(l,0,0);
				break;
			default:
				a = null;
		}
		
		return a;
	}
}
