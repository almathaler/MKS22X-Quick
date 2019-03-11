import java.util.Random;
import java.util.Arrays;
public class Quick{
  public static void main(String[] args){
    //System.out.println("I love you more -- Joseph");
    for (int i = 0; i<5; i++){
      Random rng = new Random();
      int size = rng.nextInt(10) + 1;
      int[] data = new int[size];
      for (int c = 0; c<size; c++){
        data[c] = rng.nextInt(2);
      }
      int k = rng.nextInt(size);
      //System.out.println("\ndata: " + Arrays.toString(data));
      System.out.println("the " + k + "th smallest element: " + quickselect(data, k));
      //insertionSort(data);
      //System.out.println("the " + k + "th smallest element according to insertion: "+ data[k]);
      //System.out.println(Arrays.toString(data));
      //int pivotInd = partition(data, 1, data.length-1);
      //System.out.println("correctly partitioned?: " + sortedIsh(data, pivotInd));
    }

    //System.out.println(partition(data, 1, data.length-1));
  }
  /*return the value that is the kth smallest value of the array.
 */
 //have to have this account for arrays of size 1
 public static int quickselect(int[] data, int k){
   if (data.length > 1){
     boolean foundK = false;
     while (!foundK){ //don't really need boolean, bc this loop is broken by an internal return
       int ind = partition(data, 1, data.length-1); //run partition, it's return is the final index of the partition
       if (ind == k){ //if that index is k
         foundK = true; //then that means it's the kth smallest element
         return data[ind]; //and you should return it
       }
     }
     return -1; //something broken
   }else{
     return data[0];
   }
 }



  //start = 1 and end = data.length-1
  private static int partition(int[] data, int start, int end){
    Random rng = new Random();
    int pivotInd = rng.nextInt(data.length);
    int pivot = data[pivotInd];
    swap(data, 0, pivotInd);//move pivot to back
    while (start != end){//when you still have left to compare
      if (data[start] > pivot){//compare
        swap(data, start, end);//either move start to end and move end
        end--;
      }else{
        start++;//or don't do anything, move start foward
      }
    }
    if (data[start] < pivot){ //you need to switch the start and pivot
      swap(data, start, 0);
      //System.out.println("Final index of " + pivot + " is ");
      System.out.println("data looks like: " + Arrays.toString(data));
      return start;
    }else{
      swap(data, start-1, 0);
    //  System.out.println("Final index of " + pivot + " is ");
    //  System.out.println
      return start-1;
    }
  }
  private static void swap(int[] data, int ind1, int ind2){
    int temp = data[ind1];
    data[ind1] = data[ind2];
    data[ind2] = temp;
  }
  private static boolean sortedIsh(int[] data, int pivotInd){
    for (int i = 0; i<pivotInd; i++){
      if (data[i] > data[pivotInd]){
        return false;
      }
    }
    for (int iA = pivotInd+1; iA<data.length; iA++){
      if (data[iA] < data[pivotInd]){
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
