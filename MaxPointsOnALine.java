import java.util.*;
import java.lang.Math;
  class Point {
      int x;
      int y;
      Point() { x = 0; y = 0; }
      Point(int a, int b) { x = a; y = b; }
	  public void print(){
		  System.out.println("x = "+x+" y ="+y );
	  }
  }
 
public class MaxPointsOnALine {
    public int maxPoints(Point[] points) {
		/*
			create a hashmap (slope, size of the (list of points on  this line with this slope))
			return the size of the largest list;
			
			Now the slope in double format cannot be used as an integer because the points can be put in different buckets due to precision differences while calculating the slopes. Hence, the slope of the line must be expressed as a ratio of two integers. Hence we have a two layered map: 
			map(x0, map (y0, sizeof (list of points on line with slope y0/x0 ))) where y0 = y2 - y1 and x0 = x2 - x1;
			
			Two numbers in a ratio can be represented by the same fraction if the numerators and denominators were divided by their GCD.
			Hence, 6/12 and 4/8 can be represented by 1/2 if they were divided by their GCD's 6 and 4 respectively. Hence, x0 and y0 need to be divided by their GCD's before being used as an index for the hashmap.
		*/
		
		Map<Integer,Map<Integer,Integer>> resultMap = new HashMap<Integer,Map<Integer,Integer>>();
		
		int result =0;
		for(int i=0;i<points.length;i++)
		{
			if(points==null)
				return 0;
			if(points.length<=2)
				return points.length;
			
			int overlap = 0;
			System.out.println("points[i] = ( "+points[i].x+" , "+points[i].y+" )");
			resultMap.clear(); /*because we add count for x0 =1 and y0 =1 , when points[i] = (1,1) vs points[j] =(2,2) or (3,3) we don't want to add
			to the same count when points[i] = (2,2) vs points[j] = (3,3)..we'll need a new count for points(2,2)
			*/
			int iMaxCount=0;
			for(int j=i+1;j<points.length;j++)
			{
				int x0 = points[j].x - points[i].x;
				int y0 = points[j].y - points[i].y;
				System.out.println("points[j] = ( "+points[j].x+" , "+points[j].y+" )");
				System.out.println("x0 = "+x0+" y0 ="+y0);
				
				if((x0==0)&&(y0==0)) 
				{
					 /* the only time this happens is when there are duplicate points in the input set. Therefore, we need to assume that the duplicate is on every line where point[i] is present. Hence the comparison on line 83*/
					overlap++;
				}
				else{
					int gcd = generateGCD(x0,y0);
					if(gcd!=0)
					{
						x0 = x0/gcd;
						y0 = y0/gcd;
					}
					System.out.println(" x0 = "+x0+" y0 = "+y0);
						if(resultMap.containsKey(new Integer(x0)))
						{
							if(resultMap.get(x0).containsKey(y0))
							{
								int currentCount = resultMap.get(x0).get(y0)+1;
								
								resultMap.get(x0).put(y0,currentCount);
								System.out.println("resultMap after increasing  x0 "+x0+" and y0 "+y0+" "+resultMap.toString());
								if(iMaxCount<currentCount)
									iMaxCount = currentCount;
								
							}
							else{	
									
								resultMap.get(x0).put(y0,1);
								System.out.println("resultMap after inserting y0 "+y0+" "+resultMap.toString());
							}
						}
						else{
							System.out.println("resultMap does not contain x0 = "+x0);
							Map<Integer,Integer> xMap = new HashMap<Integer,Integer>();
							xMap.put(y0,1);
							resultMap.put(x0,xMap);
							System.out.println("resultMap after inserting x0 "+x0+" "+resultMap.toString());
							
						}
						
						System.out.println("iMaxCount before = "+maxCount);
						if(iMaxCount < resultMap.get(x0).get(y0)+overlap+1)
						{
							iMaxCount = resultMap.get(x0).get(y0)+overlap+1;
							System.out.println("iMaxCount after = "+maxCount);
						}
				}
				
			}
			
			result = Math.max(result, iMaxCount+overlap+1);
			
		}
		
        return result;
    }
	
	int generateGCD(int a,int b){
		if(b==0)
			return a;
		else
			return generateGCD(b,a%b);
	}
	
	public static void main(String args[]){
		
		Point[] input = new Point[7];
		Point p1 = new Point(1,1);
		input[5]=p1;
		Point p2 = new Point (2,2);
		input[6]=p2;
		Point p3 = new Point (3,3);
		input[2]=p3;
		Point p4 = new Point (2,2);
		input[3]=p4;
		Point p5 = new Point (4,3);
		input[4]=p5;
		//checking for parallel lines
		Point p6 = new Point (0,1);
		input[0]=p6;
		Point p7 = new Point (1,2); 
		input[1]=p7;
		for(int i=0;i<input.length;i++)
			input[i].print();
		
		MaxPointsOnALine mpl = new MaxPointsOnALine();
		System.out.println("max Points = "+mpl.maxPoints(input));
	}
}