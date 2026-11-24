# kyube.: plan and roadmap

kyube. is a free, open-source document app for Android: PDFs today, then Word,
PowerPoint, Excel, notes, pictures and voice, all on one engine. No ads, no accounts, nothing leaves
the phone.

This file is the roadmap. `HANDOFF.md` is the short "where we are right now" for the next session.

## Ground rules

- **Package names stay**: app id `com.chethan616.clearpdf` (so it updates ClearPDF installs), Kotlin
  package `com.chethan616.clearpdf`. Only the product name changed.
- **Signing keys per channel** (never unify): GitHub/F-Droid CI key `92:FA:50…` (repo secrets), Play
  upload key `2C:18:8D…` (`key.properties` + `clearpdf-upload-key.jks`, local only, git-ignored).
- **Design**: the mocks in `D:\ClearPDF-latest\design\` are the spec. Liquid glass (Kyant backdrop)
  + Material 3 Expressive motion and loaders. Apple HIG colours, more playful. See "Design system".
- **Commits** as `chethan616 <chethankrishna2022@gmail.com>`, no AI trailers.
- **Credit** the engine's authors in NOTICE / Licences (MIT OR Apache-2.0 requires it), but public
  posts talk about "open-source tools", not a specific project.

## Design system (`ui/theme/ClearTheme.kt`, `ui/glass/*`)

| Token | Light | Dark |
|---|---|---|
| Wallpaper | sky gradient `#9AB4DC → #B3C5E8 → #A8BEE3` + blooms (aqua, lilac, pink, periwinkle) | "Midnight Aurora": indigo `#0A0B1E → #14123A → #0B1030` + cobalt, violet, magenta, periwinkle blooms |
| Hero titles | white + soft glow | white |
| Ink | `#172033` | `#EEF1FF` |
| Viewer surfaces | `#F5F7FB` / `#E9EDF4` | indigo `#12132B` / `#1C1D3E` / pressed `#2A2B52` |
| Chip (chosen) | lemon `#EEF27E` | same |
| Accents (`Candy`) | Apple system hues: red `#FF5B4F`, orange, yellow, green, mint, blue `#0A84FF`, indigo, pink | same |
| Card tones (`Tone`) | Sage (lists), Paper (notes), Lavender (voice), Butter (Files), Orchid (Pdf), Blush, Sky, Peach | same hues as jewels: saturated, deep, pale ink on them (`DarkTones`) |

Type: Plus Jakarta Sans (all UI), Doto (list titles, dot-matrix), DM Serif Display (collection
words). All OFL, in `res/font`, licences in `assets/licences`.

Rules: glass always refracts what is really behind it (`LocalBackdrop` provided by the layer
below, never passed by hand). Bounce only on draw-time properties. One loader everywhere:
`GlassLoader` (M3 Expressive `LoadingIndicator` in a glass circle). Mascots (`ui/mascot`, MIT
bot-avatars) for empty, waiting and error states only.

## Done

- [x] V2 base: Rust engine + UniFFI bridge, glass UI kit, viewer, tools, signing, forms, OCR.
- [x] Upstream PRs: hide bars on scroll (#9), double-tap-drag zoom (#14), live signature trust
      refresh (Android half of #22; its engine half is older than ours).
- [x] Rename to kyube. (strings in every language, icons, splash, Pictures/kyube folder).
- [x] New launcher icon (adaptive + themed monochrome) and Play/F-Droid icon from `artefact.png`.
- [x] Theme rebuild to the mocks (wallpaper, tones, type, chips).
- [x] Desk (home): header mark + menu, title per chip, chips, bento of cards, + fan, mic recorder.
- [x] Notes: text (light Markdown), to-do lists, voice memos, photos; JSON store in `files/notes`.
- [x] Welcome (onboarding), 4 pages, shown once, "Show the welcome again" in Settings.
- [x] Quick Preview (floating card over the calling app) with Expand / Share / More / Close.
- [x] M3 Expressive loader in a glass circle replaces every spinner.
- [x] Motion and speed pass (2026-11-06): Apple-curve screen/sheet motion (`Fluid`), bounce only on
      glass controls, overlays animate out, onboarding/dock/voice card read motion in draw only,
      wallpaper bitmap reused, mic opened off the main thread, Recent rows memoised, `fast` build.
- [x] Material 3 Expressive haptics (`ui/glass/Haptics.kt`: vibrator primitives, `Feel`s) on
      buttons, rows, chips, toggles, tabs, the dock, recording, mascots, sheet pull thresholds.
- [x] Voice card to the mock; voice memos can be named (toast "Name it" after saving, long press ▸ Rename).
- [x] Viewer: glass refracts the pages; iOS-style top bar (grouped buttons), sheets and alerts.
- [x] Tool screens (Compress, Combine, …, signature pad) now open over the viewer (were under it).
- [x] Quick Preview made minimal (header, pages, one Open pill; the rest in More), More menu glass
      fixed (refracts the pages), Open goes straight into the app (no full-screen stage).
- [x] Thought of the day: a Paper card second on the desk (All, Notes), opens a flagged note
      (`Note.thought` = epoch day) with the day's quote as title and a reflection as body, "Tap here
      to continue…" under it; kept only once written on. 30 quotes + reflections in 9 languages
      (`thought_quotes`, `thought_reflections`).
- [x] Glass and motion pass 2 (2026-11-06): welcome no longer shows neighbour pages at rest; menus,
      dialogs, sheets, toasts, hint toasts, the + fan and the recorder pill move on `GlassMotion`
      springs (screen transitions untouched); filter chips, card chips and check pills are
      `GlassChip`s (same glass as Back/More) and the dock capsule is `glass`; every glass part follows
      `GlassSettings.style` and `MotionSettings`; dark theme rebuilt as "Midnight Aurora" with jewel cards.
- [x] Perf and polish pass 3 (2026-11-06): welcome made light (own layers for dots and mascots, mascots
      paused off-screen, glow titles and costly cards cached while still, no offscreen alpha passes),
      Continue is a candy `GlassPill` that takes each page's colour (`tintAt`), Skip is a `GlassChip`;
      the + fan no longer recomposes per frame (scrim in draw, chips composed one by one); the dock
      capsule swells and shrinks on slower springs (`GlassMotion.shape`/`shapeBack`) so the bounce shows.
- [x] Lag pass 4 (2026-11-06): Crystal mode made cheap (shared + pre-warmed shaders, adaptive-tap
      dispersion, highlight skips re-recording), welcome cards throttle the glass under them, Jelly dock no
      longer freezes at its extremes (damping floor, capped layout overshoot), recorder X in dark ink,
      sheets rise/leave on better curves with staggered content, six distinct desk-title fonts.
- [x] Bounce pass (2026-11-06): the + fan and sheets rise on a fixed-time bounce tween (`GlassMotion.bounce`)
      instead of springs, so the glass goes quiet on a known frame; "Your workspace" in a clean bold grotesque.

- [x] 3.0.0-beta.10 (2026-11-14): first beta and first non-pre-release "Latest" on GitHub, with the
      16:9 trailer attached; releases are betas (`-beta.N`) from here on.

## Next (UI first)

- [x] Viewer: every tool sheet checked against the new sheet parts (2026-11-15): VoiceSheet now uses
      `SheetHeader`, DigitalSign's list is a `SheetSection`, FillSign has a `SheetTitle`; CommentList,
      Pages, Save as already used them; NoteSheet, ReadAloud and CommentPanel are docked bars, not
      sheets. Built and installed; [ ] not yet looked at on the phone.
- [ ] (2026-11-14, `fast` 3.0.0-beta.10 on the OnePlus: welcome, desk, + fan, Files, Tools, viewer and the Fill & Sign bar all drew and animated correctly; Crystal style and Jelly mic not yet looked at; 2026-11-15: the file row's sheet in Files drew correctly on the `fast` build, as did the dark sheet viewer) Device check of the four 2026-11-06 passes on the `fast` build (pass 4: Crystal + / welcome page 2 / Jelly mic / long-press sheet) (no phone was attached): welcome swipe
      smoothness, + fan with a busy desk, menu/dialog/sheet/toast springs, recorder pill expand/shrink
      bounce (tune `GlassMotion.shape` stiffness if still too quick/slow), GlassChip rows, dark theme.
- [x] Files / Tools / Settings checked against the mocks on the phone (2026-11-14): same glass, type and
      colour as the mocks, nothing to change.
- [x] Signature validation badges drawn by the viewer (Acrobat-style tick), never in the file.
- [x] Translations: ar, de, es, fr, it, pt-BR complete + **pt** and **ru**.
- [x] Folder restructure, README/NOTICE/ATTRIBUTION/fastlane to kyube. (9 store languages).
- [ ] Release build, `master` branch on GitHub, "Building the version 2" issue + discussion.
      (2026-11-15: [x] the arm64 release APK builds and is signed with the Play upload key, 95 MB, from
      this branch; the issue/discussion text is drafted locally; [ ] nothing pushed or published.)

- [x] Home screen widgets (2026-11-15): **Thought of the day / Notes** (square, 2×2 to 3×3), **To-Do** and
      **Recent** (square, 2×2 to 3×3, opening at 3×3 where the desk card shows whole). Each is the desk's own composable (`ListCard`, `NoteCard`,
      `RecentCard`, real liquid glass, the user's glass settings) rendered off screen on a private
      virtual display (`widget/ComposeSnapshot.kt`) at the widget's own size, Apple style: never
      scaled, the content adapts (fewer pills + "N more"; the note's body gives way to the date; the
      fan of pages shrinks). To-Do pills tick from the home screen (tap targets over the real pills,
      press ripple, the app's ToggleOn/Off haptic, ticks go through the app's live notes when it runs).
      Recent is drawn by the app when its recents change (thumbnails live there) and kept in cache.
      Seen on the phone. A widget can't run live glass or its press springs: home screens only show
      pictures and send taps.
- [x] `MorphButton` (2026-11-15, `ui/glass/MorphButton.kt` + `LiquidMorph.kt`; GlassMenu left as it was): the
      viewer's ⋯ circle itself becomes its menu, as liquid glass (iOS 26 GlassEffectContainer idea, from
      StewartLynch/Liquid-Glass-Controls-And-Views `Completed`): one shape made of the circle and a rounded
      rectangle melted by a smooth minimum of their distance fields (AGSL refraction + surface shaders on
      that outline). Three springs, each starting as the one before settles: drip (a narrower drop runs
      ~1 cm down out of the circle, joined by a liquid neck), spread (to the menu's width), pour (to its
      height; the circle melts away, only the menu is left over the button's place). ~0.7 s; close runs it
      back. Ink adapts to what is behind (white over Night mode). Android 13+; older phones get the plain
      rounded-rectangle expansion. Seen on the phone, frame by frame at 10x animator scale.
- [ ] Widgets left to look at on the phone: To-Do's scrolling list (`RemoteCollectionItems` of the real
      pills; card hugs title + pills, scrolls past the widget's height; seen hugging, ticks through the
      list not yet tried), Recent drawn at the desk card's exact size and fitted (built, not seen).

## Folder layout

```
android/          Gradle project (app, backdrop)
engine/           Rust workspace: crates/ (PDF engine), mobile/ (libartefact.so bridge),
                  assets/ (fonts, icons, OCR models), vendor/ (patched crates)
docs/             design notes and README pictures (docs/media)
fastlane/         store listings (9 languages)
tools/            i18n helper
```

## Office documents: Word, PowerPoint, Excel (in progress)

storytold's engines (same family as pdfcraft) behind the same UniFFI bridge, headless crates only
(never their egui UI). Order: Word viewing → PowerPoint viewing → Excel viewing → export to PDF for
all three → editing, one format at a time, each built and run on the phone before the next.

- [x] Research (2026-11-06): all three are MIT OR Apache-2.0, edition 2024, MSRV 1.95, and their
      engine crates build for `aarch64-linux-android`. Word and PowerPoint render pages/slides to
      RGBA (`vello_cpu`); Excel's grid is drawn only by its egui UI, so sheets get a Compose grid.
- [x] **Word viewing** (wordcraft `729cd0a`), built and unit-tested on the host:
      - `engine/vendor/wordcraft-engine` (font store behind a `font-store` feature, off: no network
        code) and `engine/vendor/wordcraft-fonts` (Android's font folders); the rest pinned by git rev.
        Upstream draft: `docs/upstream/wordcraft-android.md` (not filed).
      - Bridge (`engine/mobile/src/word.rs`): Word documents open in the same `MobileSession` under
        ids from 2^40, told from PDFs by their bytes; `DocumentInfo.kind` = `Pdf`/`Word`. Page render
        and tiles, thumbnails, links, and a text layer built into the PDF engine's `PageText`, so
        search, selection and Read Aloud work unchanged. `set_locale` for paper of formats without one.
      - Android: pickers, folder lists, intent filters (share + Quick Preview) take .docx/.doc/.odt/.rtf;
        the viewer hides the PDF tools for Word (bar = Read Aloud; More sheet without Edit/Print/
        comments/signatures/PDF tools); names keep their Word extension.
      - Installed on the phone (fast build, 2026-11-06); [x] seen there 2026-11-14 (a real .docx
        opens and renders on 3.0.0-beta.10; [ ] Recent and Quick Preview not looked at).
- [x] **PowerPoint viewing** (deckcraft `159ed07`), built and host-tested (2026-11-07): `slides.rs`
      opens .pptx with `deckcraft-pptx` and draws slides with `deckcraft-render` (no deckcraft engine,
      so no video codecs or krilla); slides are pages (`DocumentKind.Slides`), with text layer and links;
      `deckcraft-fonts` vendored with the Android font folders. App: pptx types in pickers, folder
      lists and filters; same reading-only viewer as Word. [x] seen on the phone 2026-11-14 (a 9-slide .pptx renders in the bundled stand-in fonts); [x] present mode
      (full-screen slides, tap/swipe to advance; transitions from `deckcraft-render::render_transition`).
      Password-protected decks are refused with a message (deckcraft can't decrypt). `.ppt` (97-2003)
      opens through the app's own reader (`engine/mobile/src/ppt.rs`): slides' text only, [ ] pictures not checked on a real .ppt.
- [x] **Excel viewing** (gridcraft `6a8eb4f`, 2026-11-07, built and host-tested): `sheets.rs` opens
      .xlsx/.xlsb/.ods/.csv (recalculated on open) and serves tabs, axes, cell windows with looks,
      merges, find; `ui/sheets/SheetViewer.kt` draws the grid on a canvas (pan, pinch zoom, heading
      strips, frozen panes, cells in 50×20 blocks) under glass. [x] seen on the phone 2026-11-14 (an .xlsx and a Cyrillic .xls: merges, fills, borders, bars); [x] search UI
      (bridge `sheet_find` is there); [x] charts (gridcraft-chart, `SheetCharts.kt`) and pictures on sheets (`sheetPictures`) are in the code; [ ] not yet seen on the phone.
- [x] **Pictures in notes** (lightcraft `8a0b91a`, 2026-11-07, built and host-tested): `photo.rs`
      (`PhotoEditor`: decode, develop with LightCraft's `DevelopSettings` JSON, export JPEG; codecs,
      develop, pipeline only: no GPU, faces or catalog). Tap a note's picture: glass editor with
      Exposure, Contrast, Highlights, Shadows, Vibrance, Saturation and rotate; Done saves a new JPEG.
      [x] seen on the phone 2026-11-15 (opens from Files with Adjust, Crop, Blur, Draw, Text); [x] crop (`PhotoEditor.kt`, LightCraft's crop model; not yet seen on the phone); [x] markup: a freehand **Draw** pen (width, ten colours, undo; `Pen` mark in `PhotoMarks.kt`, saved with the picture; stroke drawing seen on the phone, saving not tried).
- [x] **Export and print for all three** (2026-11-07): `export_office(doc, format, path)` /
      `export_formats(doc)`: Word → pdf, docx, odt, rtf, html, md, txt; slides → pdf, pptx; sheets →
      pdf (gridcraft's `file.exportPdf`, every sheet), xlsx, csv. Office bars: Word = Read aloud ·
      Export PDF · Save as · Print; slides add **Present** (full screen, swipe/tap, bars hidden);
      sheets: **search** (glass field, hits ringed, ↑↓). No PDF More tools for office documents.
      [x] checked gridcraft's PDF with non-Latin text 2026-11-14: **fails**. A Cyrillic .xls exports with
      the text missing (digits and Latin show). gridcraft's `pdf` crate uses the 14 standard fonts and,
      for anything WinAnsi lacks, a non-embedded Adobe CID font, which the viewer cannot draw. Needs
      embedded fonts in the export: **fixed 2026-11-15** by vendoring `gridcraft-pdf` with Source Sans 3
      subsets embedded (Cyrillic, Greek, Latin Extended; CJK/Arabic/Indic still use the old fallback).
      [x] seen on the phone 2026-11-15: a Cyrillic + Greek .csv exports (17.5 KB) and the PDF shows all
      the text in the viewer. Sheets in the dark theme use Google Sheets' neutral greys (#1F1F1F ground,
      #3C4043 lines, #2D2E30 headings) instead of the indigo.
- [x] **Editing, first pass** (2026-11-07, built, host-tested, not yet seen on the phone):
      Word: Edit → tap places the caret, the keyboard types, glass dock (undo, redo, bold, italic,
      underline, bullets, numbering, align, heading, body); saved in its own format. Sheets: tap a
      cell → glass formula bar (type a value or =formula; double tap edits at once) and a format dock
      (bold, italic, fill, align, wrap, %, thousands, insert row/column, clear, undo, redo); ⋯ menu:
      Export PDF, Save as, Print, Share; Quick Preview shows the real grid. `.xls` opens; `.ppt`
      explains itself (DeckCraft has no 97-2003 reader). Selection menu = the desk menu's frosted glass.
- [x] Word's navigation pane (2026-11-15): a document's headings (outline levels) fill Bookmarks &
      Contents with their pages (`headings_of` in `word.rs`, `OfficeDoc::outline`); seen on the phone
      (the sample's Summary, Figures, Next year; tapping one jumps to its page). Test:
      `a_word_document_lists_its_headings_with_their_pages`.
- [x] Slide notes and thumbnails (2026-11-15): More ▸ Speaker notes opens a sheet with the slide in
      view's notes (`slide_notes` in the bridge, `SlideNotesSheet`); the sheet seen on the phone on a
      21-slide deck (that deck has no notes, so only the empty state was seen; the notes text is
      covered by `a_slide_reports_its_speaker_notes`). Thumbnails: More ▸ Pages already lists slides.
- [ ] Map each engine's remaining features into the UI, one format at a time (they are large: Word
      422 commands, slides 320, sheets 313, LightCraft's develop panel): next are Word's navigation
      pane (headings), slide notes and thumbnails, sheet charts and pictures, LightCraft crop,
      presets and auto, then editing (typing, formatting) per format.
- [ ] Editing, one format at a time (wordcraft's 422 commands through `Session::run`, then deckcraft's
      and gridcraft's registries).

## Later

1. **Voice** (storytold/soundcraft) for notes; more of LightCraft (crop, presets, markup).
2. **Notes and lists on the Word engine**: once wordcraft is in (it reads and writes Markdown),
   move the notes and the shopping/to-do lists onto it instead of the app's own light Markdown, with
   a few adjustments to feel like Apple Notes: checklists, quick formatting tools (headings, bold,
   lists, tables), inline pictures, and the same note toolbar. Pictures in notes (crop, rotate,
   adjust, markup) from storytold/lightcraft.

Quick Preview's intent filter grows with each format (DOCX done; XLSX, PPTX, images, text next).
