# SumNotes Agent Guide

Welcome! This repo contains a single-module Android app written in **Java**. Use this guide to quickly orient yourself when debugging, adding features, or answering questions.

## Project structure
- `app/` &rarr; Gradle application module. Global build logic lives in `app/build.gradle.kts` (Android config + dependencies) and the root `build.gradle.kts` (top-level clean task).
- `app/src/main/java/com/example/sumnotes/`
    - `MainActivity` hosts a `NavHostFragment` defined in `res/navigation/nav_graph.xml` and is the only activity.
    - `NoteListFragment` & `NoteEditorFragment` make up the UI. Their layouts live in `res/layout/fragment_note_list.xml` and `fragment_note_editor.xml`.
    - `NoteListAdapter` backs the RecyclerView in the list fragment. Item layout: `res/layout/item_note.xml`.
    - ViewModels (`NoteListViewModel`, `NoteEditorViewModel`) expose `LiveData` from the repository layer via `ViewModelProvider` factories.
    - `DateConverter` contains static helpers that Room uses for persisting `Date` fields.
- `app/src/main/java/com/example/sumnotes/data/`
    - `NoteEntity` is the Room entity (table definition) used throughout the app.
    - `NoteDao` defines database queries for listing, inserting, updating, deleting notes.
    - `NotesDb` sets up the Room database singleton with `@Database`.
    - `NoteRepository` mediates between DAO and view models, returning `LiveData<List<NoteEntity>>`.
- `app/src/main/res/navigation/nav_graph.xml` wires the list/editor fragments and handles argument passing.
- `app/src/main/res/xml/` contains backup/data-extraction configuration consumed by the manifest.

## Common tasks & tips
- Use `rg <term>` to locate code—`ls -R`/`grep -R` are discouraged in this environment.
- When modifying UI, adjust the corresponding XML under `res/layout` and update the relevant fragment/adapter.
- Navigation actions and argument names must match IDs in `nav_graph.xml`.
- Database schema changes require updating `NoteEntity`, `NotesDb` (version bump + migrations), and DAO queries.
- ViewModels should talk only to `NoteRepository`—keep fragments lean and observe `LiveData`.

## Testing & builds
- Unit tests: `./gradlew test` (runs JVM tests under `app/src/test`).
- Instrumented tests: `./gradlew connectedAndroidTest` (requires emulator/device; skip if unavailable in CI).
- Lint/formatting: no dedicated task provided; rely on Android Studio's built-in tools when available.

## Frequently referenced files
- `app/src/main/AndroidManifest.xml` for permissions and activity declarations.
- `app/src/main/res/values/strings.xml` & `colors.xml` for localized UI strings and theme colors.
- `gradle/libs.versions.toml` pins dependency versions; check before adding libraries.

Keep this guide in mind when responding to repo-related prompts for faster, more accurate answers.