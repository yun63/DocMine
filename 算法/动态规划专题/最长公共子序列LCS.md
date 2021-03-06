## 最长公共子序列LCS

### 1 问题

> 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列。一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
> 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。

若这两个字符串没有公共子序列，则返回 0。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/longest-common-subsequence
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

### 2 分析

最长公共子序列（Longest Common Subsequence，简称 LCS）是一道非常经典的面试题目，因为它的解法是典型的二维动态规划，大部分比较困难的字符串问题都和这个问题一个套路，比如说编辑距离。而且，这个算法稍加改造就可以用于解决其他问题，所以说 LCS 算法是值得掌握的。

**为什么LCS问题是用动态规划来解决呢？**

> 子序列类型的问题，穷举出所有可能的结果并不容易，而动态规划算法就是穷举+剪枝，所以，**只要涉及到子序列问题，90%以上可能都需要动态规划来解决，往动态规划方面考虑就对了**

### 3 动态规划分析

1. 明确dp数组的含义

   对于两个字符串的动态规划问题，套路是通用的。

   对于字符串s1和s2，通常需要构建一个dp table

   ![dp](../../Assets/dp.png)

   dp\[i][j]：对于s1[1..i]和s2[1..j]的LCS长度，针对问题，“ac”和“babc”的LCS长度是2，我们最终要的答案是dp\[3][6]

2. 定义base case

   我们专门让索引为0的行和列表示空串，这样dp\[0][...]和dp\[..][0]都初始化为0

   有了base case，dp\[0][3]=0的含义就是对于空字符""和"bab"的LCS长度是0

3. 确定状态转移方程

   这是动态规划最难的一步，不过好在字符串问题的dp方程套路都差不多。

   状态转移简单地说，就是做选择，求s1和s2的最长公共子序列lcs。

   对于s1和s2中的每个字符，有什么选择？很简单，两种选择，要么在lcs中，要么不在。

   ![](../../Assets/lcs.png)

   【在】和【不在】就是选择，关键是，应该如何选择呢？

   如果某个字符在lcs中，那么这个字符一定在s1和s2中，因为lcs是最长公共子序列。

   本题的一种思路是这样的：

   用两个指针i和j从后往前遍历s1和s2，如果s1[i] == s2[j]，那么这个字符一定在lcs中；否则，s1[i]和s2[j]这两个字符至少有一个不在lcs，需要丢弃一个。我们先写一个递归算法：

   ```python
   def longest_common_subsequcence(str1, str2):
       def dp(i, j):
           # 空串的base case
           if i == -1 or j == -1:
               return 0
           if str1[i] == str2[j]:
               这边找到一个lcs的元素，继续往前找
               return dp(i - 1, j - 1) + 1
           return max(dp(i - 1, j), dp(i, j - 1))
       # i和j初始化为最后一个索引
       return dp(len(str1) - 1, len(str2) - 1)
   ```

   其实，这就是暴力解法，下面我们通过动态规划来解决这个问题。

   我们再次分析一下这个问题：

   对于两个字符串"OOOOOO?"和"XXXXXX?"，如果末尾两个？相等，那么"OOOOOO?"和"XXXXXX?"的LCS的长度就是字符串"OOOOOO"和"XXXXXX"的LCS的长度+1；

   如果末尾两个？不相等，那么"OOOOOOA"和"XXXXXXB"的LCS的长度一定是"OOOOOOA"和"XXXXXX"的LCS的长度和"OOOOOO"和"XXXXXXB"的LCS的长度的较大者，这样我们就可以写出转移方程了：

   ```python
   if str1[i] == str2[j]:
       dp[i][j] = dp[i-1][j-1] + 1
   else:
       dp[i][j] = max(dp[i-1][j], dp[i][j-1])
   ```

### 4 总结

1. 对于两个字符串的动态规划问题，一般来说，都应该定义DP table，这样容易写出状态转移方程，dp\[i][j]的状态可以通过之前的状态推到出来。

   ![推导](../../Assets/dp推导.png)