You have made a lot of progress! This submission shows good separation of concerns and you properly used the IO class for user interactions.

**OVERALL: PASSED**

Minor Tweaks:

* Double check your comments and white space. Sometimes you have typos in them and extra blank lines. It's fine to have some extra blank lines for readability, but yours aren't very consistent. Try to stick to one style.
* The `Random random` field is placed in the middle of the class, breaking the logical flow. Put those at the top of the class.
* `rentLocker()` returns a `Result` object, but this feels like it should be the service's responsibility. No need to change anything, just noticing that the service rents lockers, lockers don't rent themselves.
* In general, remove commented out code before submission (bottom of your IO class)
  * Same with Notes.java