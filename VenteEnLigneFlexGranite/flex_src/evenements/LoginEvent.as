package evenements
{
	import flash.events.Event;
	
   /* classe non utilisée */
	public class LoginEvent extends Event
	{		
		public static const LOGIN_EVENT : String = "loginEvent";
		public var utilisateur : Utilisateur;
		
	
		
		public function LoginEvent(u:Utilisateur){
			super(LOGIN_EVENT, true);
			utilisateur = u;
		}	
	}
}