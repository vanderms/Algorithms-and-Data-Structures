import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        Solution solution = new Solution();
        int[] nums = {3,3};
        int[] answer = solution.twoSum(nums, 6);
        System.out.print(Arrays.toString(answer));
    }


}

class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] answer = new int[2];

        HashMap<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length; i++){
            if(map.containsKey(target - nums[i])){
                answer[0] = map.get(target - nums[i]);
                answer[1] = i;
                break;
            }
            map.put(nums[i], i);
        }

        return answer;
    }
}