package com.wenqy.hsf;

import org.junit.Test;

import java.util.*;

/**
 * @Classname MyTest
 * @Description TODO
 * @Date 2020/9/4 17:34
 * @Created by xmn-wenqy
 */
public class MyTest {

    @Test
    public void testRotate() {
        int[][] matrix = new int[3][3];
        matrix[0][0] = 1;
        matrix[0][1] = 2;
        matrix[0][2] = 3;
        matrix[1][0] = 4;
        matrix[1][1] = 5;
        matrix[1][2] = 6;
        matrix[2][0] = 7;
        matrix[2][1] = 8;
        matrix[2][2] = 9;
        findDiagonalOrder(matrix);
    }

    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n -1 - i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n-1-j][i];
                matrix[n-1-j][i] = matrix[n-1-i][n-1-j];
                matrix[n-1-i][n-1-j] = matrix[j][n-1-i];
                matrix[j][n-1-i] = temp;
            }
        }
        return;
    }

    public void setZeroes(int[][] matrix) {
        boolean firstRowHasZero = false;
        boolean firstColHasZero = false;
        for (int i = 0; i < matrix.length; i++) { // 初始化标记
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    if (i == 0) {
                        firstRowHasZero = true;
                    }
                    if (j == 0) {
                        firstColHasZero = true;
                    }
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (firstRowHasZero) { // 首行
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
        if (firstColHasZero) { // 首列
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0];
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[] result = new int[m * n];
        int index = 0;
        List<Integer> arrays = new ArrayList<>();
        int row = 0, col = 0;
        boolean dictUp = true; //向上对角线
        while (row < m && col < n) {
            result[index++] = matrix[row][col];

            int newRow = row + (dictUp ? -1 : 1);
            int newCol = col + (dictUp ? 1 : -1);
            if (newRow < 0 || newRow == m || newCol < 0 || newCol == n) { // 处理对角线边界
                if (dictUp) { // 下一节点 [i, j + 1] 或者最后一列 [i + 1, j]
                    row += (col == n - 1 ? 1 : 0) ;
                    col += (col < n - 1 ? 1 : 0);
                } else {
                    col += (row == m - 1 ? 1 : 0);
                    row += (row < m - 1 ? 1 : 0);
                }
                dictUp = !dictUp;
            } else {
                row = newRow;
                col = newCol;
            }
        }
        return result;

    }

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        StringBuilder prefix = new StringBuilder(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            StringBuilder result = new StringBuilder();
            int j = 0;
            while (j < prefix.length() && j < strs[i].length()
                    && prefix.charAt(j) == strs[i].charAt(j)) {
                result.append(prefix.charAt(j));
                j++;
            }
            if (result.length() == 0) {
                return "";
            }
            prefix = result;
        }
        return prefix.toString();
    }

    @Test
    public void testWords() {
        reverseWords("a good   example");
    }
    public String reverseWords(String s) {
        if (s == null || "".equals(s)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        List<String> arrays = new ArrayList<String>();
        int start = -1;
        while(++start < s.length() && Character.isSpaceChar(s.charAt(start))) ; // 剔除空白字符
        StringBuilder word = new StringBuilder();
        for (int i = start; i < s.length();) {
            while (i < s.length() && !Character.isSpaceChar(s.charAt(i))) {
                word.append(s.charAt(i));
                i++;
            }
            arrays.add(word.toString());
            word.setLength(0);
            while(i < s.length() && Character.isSpaceChar(s.charAt(i))) i++; // 剔除后面空白字符
        }
        for (int i = arrays.size()-1; i > -1; i--) {
            if (i == 0) {
                result.append(arrays.get(i));
            } else {
                result.append(arrays.get(i) + " ");
            }
        }
        return result.toString();
    }

    @Test
    public void testStr() {
        strStr("aaaaa", "bba");
    }
    public int strStr(String haystack, String needle) {
        if (needle == null || "".equals(needle))
            return 0;
        if (haystack == null || "".equals(haystack))
            return -1;
        int L = needle.length(), n = haystack.length();
        for (int i = 0; i < n - L + 1; i++) {
            int a = 0, b= i;
            while (a < L && b < n && needle.charAt(a) == haystack.charAt(b)) {
                a++;
                b++;
            }
            if (a == n) {
                return i;
            }
        }
        return -1;
    }

    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len_odd = expandCenter(s,i,i);
            int len_even = expandCenter(s,i,i + 1);
            int len = Math.max(len_odd,len_even);
            // 计算对应最大回文子串的起点和终点
            if (len > end - start){
                start = i - (len - 1)/2;
                end = i + len/2;
            }
        }
        return s.substring(start,end + 1);
    }

    public int expandCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    public void reverseString(char[] s) {
        if (s == null || s.length == 0) {
            return;
        }
        int left = 0, right = s.length -1;
        while(left < right) {
            swap(s, left, right);
            left++;
            right--;
        }
    }


    public void swap(char[] s, int left, int right) {
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
    }

    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int total = 0;
        for (int i = 0; i < nums.length; i += 2) {
            total += nums[i];
        }
        return total;
    }

    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length;
        while (left < right) {
            if (numbers[left] + numbers[right] > target) {
                right--;
            } else if (numbers[left] + numbers[right] == target) {
                return new int[] {left + 1, right + 1};
            } else if (numbers[left] + numbers[right] < target) {
                left++;
            }
        }
        return new int[]{-1,1};
    }


    public int removeElement(int[] nums, int val) {
        int left = 0, right = nums.length -1;
        while (left < right) {
            if (nums[right] == val) {
                nums[left++] = nums[right-1];
            }
        }
        return left;
    }


    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length;) {
            int count = 0;
            int j = i;
            while (j < nums.length && nums[j] == 1) {
                count++;
                j++;
            }
            i = j + 1;
            if (count > max) {
                max = count;
            }
        }
        return max;
    }

    @Test
    public void test() {
        generate(5);
    }
    public int minSubArrayLen(int s, int[] nums) {
        int res = Integer.MAX_VALUE;
        int sum = 0;
        int j = 0;
        for (int i = 0; i < nums.length;i++) {
            sum += nums[i];
            while (sum >=s) {
                sum -= nums[j];
                res = Math.min(res,i - j + 1);
                j++;
            }

        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        if (numRows == 0) {
            return result;
        }
        for (int i = 0; i < numRows; i++) {
            if (result.isEmpty()) {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                result.add(list);
            } else if (i == 1) {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(1);
                result.add(list);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                List<Integer> pre = result.get(i-1);
                for (int j = 1; j < i; j++) {
                    list.add(pre.get(j-1) + pre.get(j));
                }
                list.add(1);
                result.add(list);
            }
        }
        return result;
    }

    public String reverseWords2(String s) {
        StringBuffer ret = new StringBuffer();
        int length = s.length();
        int i = 0;
        while (i < length) {
            int start = i;
            while (i < length && s.charAt(i) != ' ') {
                i++;
            }
            for (int p = start; p < i; p++) { // 翻转
                ret.append(s.charAt(start + i - 1 - p));
            }
            while (i < length && s.charAt(i) == ' ') {
                i++;
                ret.append(' ');
            }
        }
        return ret.toString();
    }

    public int findMin(int[] nums) {
        int s = 0, e = nums.length - 1;
        while (s <= e) {
            int mid = s + (e - s) / 2;
            if (nums[mid - 1] > nums[mid]) {
                return nums[mid];
            }
            if (nums[mid + 1] < nums[mid]) {
                return nums[mid+1];
            }
            if (nums[mid] > nums[0]) {
                s = mid + 1;
            } else if (nums[mid] < nums[0]) {
                e = mid - 1;
            }
        }
        return -1;
    }

    @Test
    public void testRemoveDuplicates() {
        moveZeroes(new int[]{0,0,1,1,1,2,2,3,3,4});
    }
    @Test
    public void testMoveZeroes() {
        moveZeroes(new int[]{1,0,0,3,12});
    }

    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int s = 0, e = 1;
        while (e < nums.length) {
            if (nums[s] != nums[e])
                nums[++s] = nums[e];
        }
        return s+1;
    }

    public void moveZeroes(int[] nums) {
        if (nums.length == 0) return;
        int s = 0, e = 0, n = nums.length;
        for (;e < n; e++) {
            while (e < n && nums[e] == 0) {
                e++;
            }
            if (e == n) return;
            int tmp = nums[e];
            nums[e] = nums[s];
            nums[s] = tmp;
            s++;
        }
    }

    public String modifyString(String s) {

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '?') {
                int j = i+1;
                while (j < s.length() && s.charAt(j) == '?') {
                    if (s.charAt(i-1) > 'a')
                    j++;
                }

            } else {
                res.append(s.charAt(i));
            }
        }
        return res.toString();
    }
}
