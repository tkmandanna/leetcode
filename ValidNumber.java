public class ValidNumber {
    public boolean isNumber(String s) {
       boolean e =false;
		boolean dot = false;
		boolean minus=false;
		boolean plus=false;
		boolean valid = true;
		boolean number = false;
		
		s=s.trim();
		
		if(s.length()==0)
		return false;
		
		for(int i=0;i<s.length();i++)
		{
		    System.out.println("number ="+number+"at "+i);
			if((s.charAt(i)=='-')&&(minus==false))
			{
				
				 if(i==0)
			       minus=true;
			     else if((e==true)&&(s.charAt(i-1)=='e'))
			        minus=true;
			     else
			        return false;    
			 
			        
				continue;
			}
			else if((s.charAt(i)=='+')&&(plus==false))
			{
			        if(i==0)
			            plus=true;
			        else if((e==true)&&(s.charAt(i-1)=='e'))
			            plus=true;
			        else
			            return false; 
				
				continue;
			}
			else if((s.charAt(i) >= 48)&&(s.charAt(i) <= 57))
			{
				System.out.println(s.charAt(i)+"is a number");
				number = true;
				continue;
			}
			else if((s.charAt(i) == 'e')&&(e == false)&&(i != (s.length()-1))&&(number==true))
			{
				//if((i==1)&&((plus == true)|| (minus == true))&&(s.length()==2))
					//return false;
					
					//if((s.charAt(0)=='.')&&(i==1))
					//return false;
					
				e=true;
				    
				continue;
			}
			else if((s.charAt(i) == '.')&&(dot == false)&&(s.length()!=1)&&(e!= true))
			{
				if((i==1)&&((plus == true)|| (minus == true))&&(s.length()==2))
					return false;
					System.out.println("dot is true at "+i);
					
				dot=true;
				continue;
			}
			else{
				//System.out.println(s.charAt(i)+"is not valid");
				valid = false;
				return valid;
			}
		}
		
		return valid;
    }
	
	public static void main(String args[])
	{
		ValidNumber vnum = new ValidNumber();
		/*System.out.println("abc is "+vnum.isNumber("abc"));
		System.out.println("2e10 is "+vnum.isNumber("2e10"));
		System.out.println("0.1 is "+vnum.isNumber("0.1"));*/
		System.out.println("3."+vnum.isNumber("-."));
	}
}
