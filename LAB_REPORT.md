# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/REPLACE_WITH_GITHUB_USERNAME/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | 23.0.1 |
| Maven | 3.10.0 |
| IDE | 2.53.0.window.3 |
| SonarQube for IDE | 6.1.1 |
| AI tool and exact model | Cursor Grok 4.6 |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: `090f7ec`

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 2 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 3 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 4 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 5 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 6 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 7 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 8 | java:S108 | ParcelService.java:80 | Nested blocks of code should not be left empty. |
| 9 | java:S1104 | Parcel.java:7 | Make this field a static final constant or non-public and provide accessors if needed. |
| 10 | java:S2384 | ParcelService.java:20 | A mutable collection should not be returned directly. |
| 11 | java:S2095 | Main.java:8 | Use try-with-resources or close this "Scanner" in a "finally" clause. |
| 12 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |

The Problems panel also listed Java-extension warnings such as a resource leak on `Scanner`. Those rows are left out of Table 1 unless SonarQube also reported them, as in row 11.

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 21 October 2026 |
| AI tool and exact model | Cursor Grok 4.6 |
| Commit reviewed | `090f7ec` |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Opened the four Java files in the workspace and ran Prompt A with `<folder>` set to `parcelpoint` |
| Output file in the reviews folder | `reviews/parcelpoint-review.md` |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | ParcelService.java:42 | Parcel codes compared with `==` | both | java:S4973 | yes, because `store` and `pickup` use different String objects |
| 2 | ParcelService.java:76 | `FileWriter` never closed | both | java:S2095 | yes, `parcels.csv` was empty after the first run |
| 3 | ParcelService.java:80 | Empty `catch` | both | java:S108 | yes, the block has no comment and no handling |
| 4 | ParcelService.java:6 | Unused import `java.util.Date` | both | java:S1128 | yes, `Date` is never used |
| 5 | ParcelService.java:12 | Unused field `maxParcels` | both | java:S1068 | yes, nothing reads it |
| 6 | Parcel.java:7 | Public field `status` | both | java:S1104 | yes, `ParcelService` writes the field directly |
| 7 | ParcelService.java:20 | Live locker list returned | both | java:S2384 | yes, callers can mutate `lockers` |
| 8 | Main.java:8 | `Scanner` never closed | both | java:S2095 | yes, `in` is never closed |
| 9 | ParcelService.java:30 | `System.out` in the service | both | java:S106 | yes as a smell; kept, because this is a command-line demo |
| 10 | ParcelService.java:64 | `printParcels(boolean)` mixes two jobs | AI | control coupling; logical cohesion | yes, the flag chooses between two outputs |
| 11 | ParcelService.java:9 | One class stores, prices, prints, "sends SMS" and writes a file | AI | weak class cohesion | yes, the methods do not serve one kind of attribute change |
| 12 | Parcel.java:6 | Size codes `1/2/3` shared by several classes | AI | connascence of convention | yes, `fee` and locker matching must treat the ints the same way |
| 13 | ParcelService.java:28 | Service writes `parcel.status` | AI | content coupling | yes, it reaches into another object's field |
| 14 | ParcelService.java:52 | Free days and rates live only in `fee` | AI | connascence of algorithm | yes, another class that priced parcels would copy the same numbers |
| 15 | Main.java:9 | `System.out` for commands | SonarQube | java:S106 | yes as a rule match; kept, because printing is the program's user interface |
| 16 | Main.java:10 | Command loop cohesion | AI | logical cohesion | no, too vague: a menu is meant to dispatch commands, so logical cohesion is expected |

2.1 Name two problems that only the AI review found.

The boolean flag on `printParcels` (control coupling) and the shared size codes `1/2/3` (connascence of convention). SonarQube did not name either in those course terms.

2.2 Name one problem that only SonarQube found, or write none.

`java:S106` on `Main.java:9`. The AI saw the same `System.out` calls but treated them as a Note for a console program, not as a defect.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.

Finding 16 called the command loop a cohesion issue and then said it was not a defect. That is too vague to act on. I opened `Main.java` and checked that each branch only parses a command and calls `ParcelService`, which is what a CLI menu should do.

Each other AI row was checked against the starter file at the given line. The `==` bug and the unclosed writer were also checked by running `store P1 6501 2`, `pickup P1 5`, `staff` on commit `090f7ec`: the fee was `0.0`, the parcel stayed `STORED`, and `parcels.csv` was empty.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Fixed | `2b5031c` |
| 2 | Fixed | `2b5031c` |
| 3 | Kept | Printing is the output of this command-line program. |
| 4 | Fixed | `2b5031c` |
| 5 | Kept | Printing is the output of this command-line program. |
| 6 | Kept | Printing is the output of this command-line program. |
| 7 | Fixed | `2b5031c` |
| 8 | Fixed | `2b5031c` |
| 9 | Fixed | `2b5031c` |
| 10 | Fixed | `2b5031c` |
| 11 | Fixed | `2b5031c` |
| 12 | Kept | Printing is the output of this command-line program. |

Number of SonarQube issues that remain after your fixes: 4 (`java:S106` on console output in `Main` and `ParcelService`)

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Connascence of convention | Replaced size and status codes with `ParcelSize` and `ParcelStatus` enums | `fb4a6bb` |
| Control coupling | Replaced `printParcels(boolean)` with `printStaffParcels` and `printWaitingParcels` | `794bff9` |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text
Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit
SMS to 6501: parcel P1 is in locker 2
Locker 2
Fee: 30.0
P1 6501 PICKED_UP
Free lockers: 3
```

```text
P1,PICKED_UP
```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 21 October 2026 | 21 October 2026 |
| AI tool and exact model | Cursor Grok 4.6 | Cursor Grok 4.6 |
| Prompt (B or C as given, or your changed prompt in full) | Prompt B as given | Prompt C as given |
| Builds with mvn package (yes or no) | yes | yes |
| Number of classes, interfaces, enums and records | 1 class | 6 classes, 1 interface, 2 enums |
| SonarQube issues, total | 11 | 4 |
| SonarQube issues, by rule | java:S2386 (3), java:S1444 (2), java:S106 (6) | java:S106 (4) |
| AI review findings in Check 1 | 5 | 3 |
| AI review findings in Check 2 | 6 | 0 problems (3 notes) |
| Review output file in the reviews folder | `reviews/bikeshare-poor-review.md` | `reviews/bikeshare-good-review.md` |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

No. It reported public static mutable data (`java:S2386`, `java:S1444`) and `System.out` (`java:S106`). None of those messages name cohesion, coupling or connascence. The boolean flag, the parallel lists and the mixed `handle` method did not appear as design rules.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

Yes. For the poor version it reported common coupling on the public static lists and control coupling on `handle(boolean isReturn, ...)`. The good-version review has no matching finding: fleet state is private in `Fleet`/`Bike`, and rent versus return are two methods.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

`bikeshare-good`. A visitor is one new `MemberType` constant with `4` baht per minute. `RentalService` and the CSV writer already take `MemberType`, so they do not need a new `if`. In `bikeshare-poor` the type is an int code inside a boolean-flag method, so a third code would be copied into the command parser and the fee branch.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

SonarQube checks local code quality: unused members, `==` on strings, resources that are never closed, empty catches, public fields and `System.out`. In ParcelPoint it found the pickup bug and the unclosed `FileWriter`. It does not check course design quality. It never said that `printParcels(boolean)` is control coupling, or that size `1/2/3` is connascence of convention, and it did not describe `bikeshare-poor` as one class doing too many jobs.

5.2 When should you not trust the AI review? Give an example from this lab.

Do not trust a finding that you cannot point to in the file, or that mixes a real observation with a grade-like judgement. In Task 2 the AI labelled the `Main` command loop as logical cohesion and then said that was normal. That row does not tell you whether to change the code. I opened the line, compared it with the other findings, and kept the menu.

5.3 What will you do differently in Project Phase 2 after this lab?

Run SonarQube for IDE on every Java file and fix the bugs and empty catches first. Then run Prompt A and keep only the cohesion, coupling and connascence rows that I can show in the code. I will also generate a small poor-versus-good sketch before I lock the Phase 2 class diagram, so that int codes and boolean flags do not land in the design we submit.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| Cursor Grok 4.6 | Prompt A review of starter ParcelPoint | `reviews/parcelpoint-review.md` | Opened each cited line; ran the C2 commands on commit `090f7ec` |
| Cursor Grok 4.6 | Fix ParcelPoint after Table 1 and two design problems | `parcelpoint/src/main/java/parcelpoint/*` | `mvn package` and the C2 commands; fee became `30.0` and `parcels.csv` contained `P1,PICKED_UP` |
| Cursor Grok 4.6 | Prompt B poor BikeShare | `bikeshare-poor/**` | `mvn package`; rented at Library and returned at Dorm for 20 baht |
| Cursor Grok 4.6 | Prompt C good BikeShare | `bikeshare-good/**` | `mvn package`; same rental story; visitor would be one enum constant |
| Cursor Grok 4.6 | Prompt A reviews of both BikeShare versions | `reviews/bikeshare-poor-review.md`, `reviews/bikeshare-good-review.md` | Compared findings with the source; Sonar-style rules counted from public static fields and `System.out` |
| Cursor Grok 4.6 | Fill this report from the tool versions and git log | `LAB_REPORT.md` | JDK, Maven, VS Code, SonarQube for IDE versions taken from the Part A commands and the installed extension 6.1.1 |
