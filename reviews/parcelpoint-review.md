1. Files read: `parcelpoint/src/main/java/parcelpoint/Parcel.java`, `Locker.java`, `ParcelService.java`, `Main.java`.

2. Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | ParcelService.java:42 | Probable bug: parcel codes are compared with `==`, so pickup of a typed command never matches a stored parcel. | Must fix | Compare with `equals()`. |
| 2 | Check 1 | ParcelService.java:76 | Probable bug: `FileWriter` is never closed, so `parcels.csv` may stay empty and the file handle leaks. | Must fix | Use try-with-resources. |
| 3 | Check 1 | ParcelService.java:80 | Empty `catch (IOException e)` swallows save failures. | Must fix | Log or print the exception message. |
| 4 | Check 1 | ParcelService.java:6 | Unused import `java.util.Date`. | Should fix | Remove the import. |
| 5 | Check 1 | ParcelService.java:12 | Unused field `maxParcels`. | Should fix | Remove the field. |
| 6 | Check 1 | Parcel.java:7 | Public field `status` can be changed by any class. | Should fix | Make the field private and add a getter and setter. |
| 7 | Check 1 | ParcelService.java:20 | `getLockers()` returns the live list, so callers can add or remove lockers. | Should fix | Return an unmodifiable copy. |
| 8 | Check 1 | Main.java:8 | `Scanner` is never closed. | Should fix | Use try-with-resources. |
| 9 | Check 1 | ParcelService.java:30 | `System.out` used for an SMS-style side effect inside the service. | Note | Keep it if this is a command-line demo, or move printing to `Main`. |
| 10 | Check 1 | ParcelService.java:64 | `printParcels` mixes two outputs in one method and is harder to read than two small methods. | Note | Split staff and waiting lists. |
| 11 | Check 2 | ParcelService.java:9 | Weak class cohesion: `ParcelService` stores parcels, prices them, prints lists, pretends to send SMS, and writes a file. | Should fix | Keep locker operations here and move pricing or file saving out. |
| 12 | Check 2 | ParcelService.java:64 | Control coupling: `printParcels(boolean forStaff)` uses a flag to choose behaviour. Cohesion of this method is logical. | Should fix | Replace the flag with two methods. |
| 13 | Check 2 | Parcel.java:6 | Connascence of convention: size `1/2/3` is a magic code shared by `Parcel`, `Locker` and `fee`. | Should fix | Use an enum for size. |
| 14 | Check 2 | ParcelService.java:28 | Content coupling: `ParcelService` writes `parcel.status` directly instead of asking `Parcel` to change its own state. | Should fix | Call a setter or a domain method on `Parcel`. |
| 15 | Check 2 | ParcelService.java:52 | Connascence of algorithm: free days `3` and rates `10/15/20` live only in `fee`, while size codes live in other classes. | Should fix | Keep the rate on the size type. |
| 16 | Check 2 | Main.java:10 | The command loop has logical cohesion (one branch per command). That is normal for a menu, not a defect. | Note | Leave the menu in `Main`; do not fold domain rules into it. |
| 17 | Check 2 | ParcelService.java:24 | Stamp coupling: `storeParcel` takes a whole `Parcel` when it needs identity, size and student id. Acceptable at this size. | Note | Keep the `Parcel` argument. |

3. Number of findings: Check 1 = 10, Check 2 = 7.
