package two_sum;

import java.util.*;


public class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> mapa = new HashMap<>();
        List<Integer> ints = new LinkedList<>();


        for (int i = 0; i < nums.length; i++) {
            int subtracao = target - nums[i];

            if (mapa.containsKey(subtracao)) {
                ints.add(mapa.get(subtracao));
                if (target - subtracao == nums[i]){
                    mapa.put(nums[i], i );
                    ints.add(mapa.get(nums[i]));
                }
            }

            mapa.put(nums[i], i);
        }
        int[] n = new int[ints.size()];

        for (int i = 0; i < ints.size(); i++){
            n[i] = ints.get(i);
        }

        return n;
    }

    public static void main(String[] args) {
        Solution sul = new Solution();
       int[] integers = sul.twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(Arrays.toString(integers));
    }
}

