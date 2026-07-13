# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

This changelog starts at 1.1.9. For earlier releases, see the
[commit history](https://github.com/3AP-AG/harvest-client/commits/master) and
[Maven Central](https://central.sonatype.com/artifact/ch.aaap/harvest-client).

## [1.1.9] - 2026-07-13

### Fixed
- Deserialization no longer throws `IllegalStateException` when the Harvest API
  returns a null `group_id` inside a time entry's `external_reference`.
  `ExternalService.getGroupId()` is now nullable. (MSK-352)

### Changed
- Publishing migrated from the retired Sonatype OSSRH to the Maven Central
  Publishing Portal. The published coordinates are unchanged
  (`ch.aaap:harvest-client`).

[1.1.9]: https://github.com/3AP-AG/harvest-client/releases/tag/v1.1.9
