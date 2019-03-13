import java.util.Random;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
public class Quick{
  //notes: for partition, start should be 1 not 0, 0th index reserved for swapping w pivot
  //keep this in mind when reading over quicksort
  public static void main(String[] args){
    for (int i = 0; i<25; i++){
      Random rng = new Random();
      int size = rng.nextInt(500);
      int[] data = new int[size];
      int[] dataSorted = new int[size];
      for (int c = 0; c<size; c++){
        int toAdd = rng.nextInt(50000);
        data[c] = toAdd;
        dataSorted[c] = toAdd;
      }
     Arrays.sort(dataSorted);
     int k = rng.nextInt(size);
     if (dataSorted[k] == quickselect(data, k)){
       System.out.println("TRUE!");
     }else{
       System.out.println("FALSE");
     }

    }
    //for (int i = 0; i<25; i++){
    //  for (int i = 0; i<10; i++){
    //int[] data = new int[25];
    /*
    for (int i = 0; i<25; i++){
      Random rng = new Random();
      int size = rng.nextInt(500);
      int[] data = new int[size];
      int[] dataSorted = new int[size];
      for (int c = 0; c<size; c++){
        int toAdd = rng.nextInt(50000);
        data[c] = toAdd;
        dataSorted[c] = toAdd;
      }
      //System.out.println(Arrays.toString(data));
      //System.out.println("The array: " + Arrays.toString(data));
      try{
        quicksort(data);
        Arrays.sort(dataSorted);
        boolean theSame = true;
        for (int k = 0; k<data.length; k++){
          if (data[k] != dataSorted[k]){
            theSame = false;
          }
        }
        if (theSame){
          System.out.println("True! Works");
        }else{
          System.out.println("\nNOT THE SAME\n");
        }
      }catch (IllegalArgumentException e){
        System.out.println(e);
      }
      //System.out.println("Sorted: " + Arrays.toString(data));
    //  System.out.println("correctly partitioned?: " + sortedIsh(data, pivotInd));
      //int k = rng.nextInt(size);
      //System.out.println("\ndata: " + Arrays.toString(data));
      //System.out.println("the " + k + "th smallest element: " + quickselect(data, k));
      //insertionSort(data);
      //System.out.println("the " + k + "th smallest element according to insertion: "+ data[k]);
      //System.out.println(Arrays.toString(data));
      //int pivotInd = partition(data, 1, data.length-1);
      //System.out.println("correctly partitioned?: " + sortedIsh(data, pivotInd));
    }
    */
  //System.out.println(partition(data, 1, data.length-1));
  //  }
  //}
 }
  /*return the value that is the kth smallest value of the array.
 */
 //have to have this account for arrays of size 1
 /*Modify the array to be in increasing order.
 */
 public static void quicksort(int[] data){
   //recursively, do partition(data, 1, data.length - 1) and then partition of that
   //partition call's return like this: partition(data, 1, return - 1) and also do other side:
   //partition(data, return + 1, data.length-1)
  try{
    quicksortHelp(data, 0, data.length - 1);
  }catch (IllegalArgumentException e){
    //if the array is size 0, don't do anything to it
  }

 }
 private static void quicksortHelp(int[] data, int start, int end){ //here start and end represent beginning of array to be processed and end
   if (start<=end){ //start-end != -1 && start-end != 1 see <-- this is best. go on until they pass each other, then stop. will
                    //do that last swapping part, if they're equal returns the start, and even tho you're calling qsH on invalid indexes, this if statement won't execute
     //System.out.println("start, end: " + start + ", " + end);
     int pivotInd = partition(data, start, end);
    // System.out.println("pivotInd: " + pivotInd);
    // System.out.println("Array currently looks like: " + Arrays.toString(data));
     quicksortHelp(data, start, pivotInd-1); //less than half, don't wanna touch pivot
     quicksortHelp(data, pivotInd+1, end); //don't wanna affect pivot, point before start is swapped w pivot -->NVM UPDATED SO START IS MOVED UP AND START INDEX MADE PIVOT
   }//do one last call
  //quicksortHelp(data, start, end);
  //what's above should do the last sort of data having 2 members, choosing to swap or no
 }
 //returns which element is at kth index in sorted array
 public static int quickselect(int[] data, int k){
   //System.out.println("\nIN NEW QUICKSELECT CALL Searching for " + k);
   if (data.length > 1){
     boolean foundK = false;
     while (!foundK){ //don't really need boolean, bc this loop is broken by an internal return
       int ind = partition(data, 0, data.length-1); //run partition, it's return is the final index of the partition
       //add to k, the index to return, however many duplicates there are of each number. For example
       //of [1, 0, 0, 2, 3, 4, 4], if you want to get the 2rd smalles number you have to return the 3rd index, bc
       //there are two 0s. only run this until the end of k, ones past k don't matter.
       if (ind == k){ //if that index is k
         foundK = true; //then that means it's the kth smallest element
         //int numToReturnIndex = nextNotDuplicate(data, ind); //check that it's not the same as what's behind it. If it is, return the one after it or after that...
         //return numToReturnIndex;
         return data[ind];
       }
     }
     return -1; //something broken
   }else{
     return data[0];
   }
 }


  //start = 1 and end = data.length-1
  //returns final index of chosen pivot
  public static int partition(int[] data, int start, int end){
    if (data.length == 0){
      throw new IllegalArgumentException("can't process empty data");
    }
    if (start == end){
      return start; //don't touch the array
    }
    Random rng = new Random();
    int pivotInd;
    int pivot;
    if (data[start] > data[end] && data[start] < data[(end+start)/2] || data[start] < data[end] && data[start] > data[(end+start)/2]){
      pivot = data[start];
      pivotInd = start;
    }else if (data[end] > data[start] && data[end] < data[(end+start)/2] || data[end] < data[start] && data[end] > data[(end+start)/2]){
      pivot = data[end];
      pivotInd = end;
    }else{
      pivotInd = (end+start)/2; //refrain from using 0s and data.length - 1, bc in quicksort we do subsets not whole array
      pivot = data[pivotInd];
    }
    //there might be a problem w this ^^. in an array of size 2, data[(data.length-1)/2] will just be data[0], which is Start
    //making first and second if statements false. so will default to the last statement, which will make pivot start ajd the bit
    //at the end
    //actaully it's not a problem bc pivot moved to 0, start can = end skipping the while loop
    //and end statemetns will be evaluated, which just compare start (1) and pivot (0)
    //System.out.println("PIVOT: " + pivot + "\nPIVOT INDEX: " + pivotInd);
  //  System.out.println("Array before movements: " + Arrays.toString(data));
 //this bariavle exists so partition called on sub array won't affect other parts
    int pivotAtStartInd = start;
    swap(data, pivotAtStartInd, pivotInd);//move pivot to back
    start++;
    while (start != end){//when you still have left to compare
      int dealWDupes = rng.nextInt(2);
      if (data[start] > pivot || data[start] == pivot && dealWDupes == 0){//compare
        swap(data, start, end);//either move start to end and move end
        end--;
      }else{
        start++;//or don't do anything, move start foward
      }
    //  System.out.println("\nArray now: " + Arrays.toString(data));
    //  System.out.println("Start: " + start + " End: " + end);
    }
    if (data[start] < pivot){ //you need to switch the start and pivot
      swap(data, start, pivotAtStartInd);
  //    System.out.println("Moved pivot to appropriate index: " + start + "\n" + Arrays.toString(data));
      //System.out.println("Final index of " + pivot + " is ");
      //System.out.println("data looks like: " + Arrays.toString(data));
      return start;
    }else{
      swap(data, start-1, pivotAtStartInd);
    //  System.out.println("Moved pivot to appropriate index: " + (start-1) + "\n" + Arrays.toString(data));
    //  System.out.println("Final index of " + pivot + " is ");
    //  System.out.println
      //System.out.println("data looks like: " + Arrays.toString(data));
      return start-1;
    }
  }
  //
  //
  //for testing
  //
  //
  private static void swap(int[] data, int ind1, int ind2){
    int temp = data[ind1];
    data[ind1] = data[ind2];
    data[ind2] = temp;
  }
  private static boolean sortedIsh(int[] data, int pivotInd){
    for (int i = 0; i<pivotInd; i++){
      if (data[i] > data[pivotInd]){
        System.out.println("data[" + i + "]: " + data[i] + " is not less than or equal to " + data[pivotInd]);
        return false;
      }
    }
    for (int iA = pivotInd+1; iA<data.length; iA++){
      if (data[iA] < data[pivotInd]){
        System.out.println("data[" + iA + "]: " + data[iA] + " is not greater than or equal to " + data[pivotInd]);
        return false;
      }
    }
    return true;
  }
  private static void insertionSort(int[] ary){
    for (int i = 1; i<ary.length; i++){ //for every int in the array, ignoring the first
      int current = ary[i]; //make a variable w that value
      int j = i-1; //make another value that will allow us to get the value right before it
      while ( j >= 0 && ary[j] > current){ //we're gonna decrease j -- while every index is greater than current
        ary[j+1] = ary[j]; //move them up one (at first, there will be 2 of ary[j], doesn't matter since current is stored;)
        j--; //it'll go like abcc to abbc to aabc, finally once either you reach 0 or re no longer getting values less than current
      }
       //if not at end, you stopped cuz something is smaller. put it one ahead of the smaller one. this is ok because of the aabc pattern
      ary[j+1] = current;

    }
  }
 }
