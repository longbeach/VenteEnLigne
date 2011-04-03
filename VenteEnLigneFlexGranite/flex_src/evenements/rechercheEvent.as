package evenements
{
	
	import flash.events.Event;
	
	public class rechercheEvent extends Event
	{
		
		public function rechercheEvent( type:String, crit:String){
			super(type);
			critere = crit;
		}
				
		public var critere : String;

	}
}