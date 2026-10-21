1. Files read: `bikeshare-good/src/main/java/bikeshare/Main.java`, `MemberType.java`, `Station.java`, `Bike.java`, `Fleet.java`, `Ride.java`, `RideRepository.java`, `CsvRideRepository.java`, `RentalService.java`.

2. Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | Main.java:9 | `System.out` is used for the command-line menu and results. | Note | Keep it; this is a console program. |
| 2 | Check 1 | CsvRideRepository.java:18 | Save failures are printed to `System.err` and then ignored. | Note | Re-throw if a later phase needs the caller to know. |
| 3 | Check 1 | Main.java:11 | The menu does not check that a line has enough words before it reads `words[1]`. | Should fix | Guard on `words.length` before parsing. |
| 4 | Check 2 | Main.java:16 | Stamp coupling at the boundary: `Main` parses tokens and passes `MemberType` and `Station` into `RentalService`. That is appropriate, not too tight. | Note | Leave the parsing in `Main`. |
| 5 | Check 2 | CsvRideRepository.java:6 | Data coupling: `RentalService` depends on the `RideRepository` interface, not on the CSV file. | Note | not found as a problem. |
| 6 | Check 2 | MemberType.java:13 | Adding a visitor rate is a new enum constant with its own baht per minute. No other class needs a new `if`. | Note | not found: no cohesion or coupling defect for a new member type. |

3. Number of findings: Check 1 = 3, Check 2 = 0 problems (3 notes). Cohesion weaker than communicational: not found. Coupling tighter than data coupling: not found, except ordinary stamp coupling at `Main`.
