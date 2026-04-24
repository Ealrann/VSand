# Liquid diagnostics

These scenarios are small deterministic inputs for the headless dump runner.

The dump metrics are material-id based, so the analyzer works for every pressure-managed liquid.
The scenario text format does not have variables yet; duplicate a scenario and replace `Water`
with another liquid material when you want a manual dump for a specific material.

The `all/` scenarios place every pressure-managed liquid in separate bays, so a single dump run
can compare spread and bottom-connected column height across materials. Acid is included there,
but it can slowly dissolve wall boundaries because that is part of its normal game behavior.
