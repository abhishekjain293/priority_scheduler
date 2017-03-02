 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.Date;
import java.util.TimerTask;
import java.util.Calendar;

class Task
{
    String job;
    float priority;
 
    /** Constructor **/
    public Task(String job, float priority)
    {
        this.job = job;
        this.priority = priority; 
    }
    /** toString() **/
    public String toString() 
    {
        return "Job Name : "+ job +"\nPriority : "+ priority;
    }
}
 
/** Class PriorityQueue **/
class PriorityQueue
{
    private Task[] heap; 
    private int heapSize, capacity;
 
    /** Constructor **/
    public PriorityQueue(int capacity)
    {    
        this.capacity = capacity + 1;
        heap = new Task[this.capacity];
        heapSize = 0;
    }
    /** function to clear **/
    public void clear()
    {
        heap = new Task[capacity];
        heapSize = 0;
    }
    /** function to check if empty **/
    public boolean isEmpty()
    {
        return heapSize == 0;
    }
    /** function to check if full **/
    public boolean isFull()
    {
        return heapSize == capacity - 1;
    }
    /** function to get Size **/
    public int size()
    {
        return heapSize;
    }
    /** function to insert task **/
    public void insert(String job, float priority)
    {
        Task newJob = new Task(job, priority);
 
        heap[++heapSize] = newJob;
        int pos = heapSize;
        while (pos != 1 && newJob.priority > heap[pos/2].priority)
        {
            heap[pos] = heap[pos/2];
            pos /=2;
        }
        heap[pos] = newJob;    
    }
	public float prior()
	{
		return(heap[1].priority);
	}
	public String jobname()
	{
		return(heap[1].job);
	}
    /** function to remove task **/
    public String remove()
    {
        int parent, child;
        Task item, temp;
        if (isEmpty() )
        {
            System.out.println("Heap is empty");
            return null;
        }
 
        item = heap[1];
        temp = heap[heapSize--];
 
        parent = 1;
        child = 2;
        while (child <= heapSize)
        {
            if (child < heapSize && heap[child].priority < heap[child + 1].priority)
                child++;
            if (temp.priority >= heap[child].priority)
                break;
 
            heap[parent] = heap[child];
            parent = child;
            child *= 2;
        }
        heap[parent] = temp;
 
        return item.job;
    }
}
 



public class queueProcess {
	
	
	String[] dates=new String[5];
	static PriorityQueue pq = new PriorityQueue(10);
	static final float[] seconds= new float [5];
	String file,tim;
	
	queueProcess(String f, String t) {
        file = f;
        tim = t;
    }
	
    public void readFile(queueProcess t1) {
 
        BufferedReader br = null;
		
		SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		fromFormat.setLenient(false);
		
        try {
            br = new BufferedReader(new FileReader(t1.file));
            String line = null;
			
			int i=0;
			String dateStr;
			String[] value=new String[4];
			
            while ((line = br.readLine()) != null) {
				value[0]="-1";
				value[1]="-1";
				value[2]="-1";
				value[3]="-1";
				
                String[] values = line.split(",");
				
				int j=0;
                for (String str : values) {
				
					value[j]=values[j];
					j++;
				}
					
					dates[i]=values[2];
					
					
					dateStr = values[2];
					try{
					Date date1 = fromFormat.parse(dateStr);
					
					}
					catch (Exception e) {
						 //The handling for the code
					}
					
                //System.out.println();
				try{
				
				
				seconds[i] = (fromFormat.parse(t1.tim).getTime()-fromFormat.parse(dateStr).getTime())/1000;
				
				if(value[3]!="-1")
				{
					
					seconds[i]=seconds[i]-(Float.parseFloat(value[3]))/100;
					
				}
				pq.insert(values[1], seconds[i]);
				
				i++;
				
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
            }
			
			
			
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
		
		
		
        queueProcess test = new queueProcess(args[0],args[1]);
		SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Calendar cal = Calendar.getInstance();
		
		try{
		Date date = fromFormat.parse(args[1]);
		cal.setTime(date);
		
		Timer timer = new Timer(true);
		
        test.readFile(test);
		
		
		Scanner scan = new Scanner(System.in);
		
		int i=0;
		
		float secondss,secondss1;
		secondss=pq.prior()*-1*1000;
		System.out.println();
		
			while(!pq.isEmpty())
			{
				
				Thread.sleep((int)secondss);
				if(i!=0){
					
					cal.add(Calendar.MILLISECOND, (int)secondss);
					String str = String.format("Current Time : [ %tc ]", cal.getTime() );
					
					secondss1=pq.prior()*-1*1000;
					System.out.println(str+", Event \" "+pq.remove()+" \" Processed" );
					
					secondss=pq.prior()*-1*1000-secondss1;
				}
				i++;
				
				
				
			}
		}
		catch (Exception e)
		{
			
		}
	}
}