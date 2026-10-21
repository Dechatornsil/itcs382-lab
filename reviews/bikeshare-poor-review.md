1. Files read: `bikeshare-poor/src/main/java/bikeshare/BikeShare.java`.

2. Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | BikeShare.java:10 | Public static mutable lists `bikes`, `locations` and `rentedBy` can be changed from anywhere. | Must fix | Make the lists private instance fields. |
| 2 | Check 1 | BikeShare.java:13 | Public static int codes `studentCode` and `staffCode` replace a type. | Should fix | Use an enum for member type. |
| 3 | Check 1 | BikeShare.java:49 | Method `handle` is long and mixes rent, return, pricing and file saving. | Should fix | Split rent, return and saving. |
| 4 | Check 1 | BikeShare.java:10 | Parallel lists for the same bike are easy to desynchronise. | Should fix | Replace the three lists with one bike object. |
| 5 | Check 1 | BikeShare.java:62 | Receipt and fee rules sit inside the return branch as string and int literals. | Note | Keep the tidy file handling; move the rate out of the branch. |
| 6 | Check 2 | BikeShare.java:49 | Logical cohesion: `handle(boolean isReturn, ...)` does one of two unrelated jobs based on a flag. | Must fix | Use two methods, `rent` and `returnBike`. |
| 7 | Check 2 | BikeShare.java:10 | Common coupling: every operation reads and writes the same public static lists. | Must fix | Hide the lists inside one inventory object. |
| 8 | Check 2 | BikeShare.java:49 | Control coupling: callers pass a boolean that changes what `handle` does. | Must fix | Drop the flag. |
| 9 | Check 2 | BikeShare.java:10 | Connascence of position: index `i` must mean the same bike in `bikes`, `locations` and `rentedBy`. | Must fix | Store the data in one record per bike. |
| 10 | Check 2 | BikeShare.java:13 | Connascence of convention: `1` means student and `2` means staff in the command and in the fee branch. | Should fix | Use an enum, as in the good version. |
| 11 | Check 2 | BikeShare.java:8 | Weak class cohesion: one class is the menu, the fleet, the fee rule and the CSV writer. | Should fix | Give each of those jobs its own class. |

3. Number of findings: Check 1 = 5, Check 2 = 6.
