package org.sheepy.vsand.audio;

import org.sheepy.lily.game.api.audio.AudioConfiguration;
import org.sheepy.lily.game.api.audio.IAudioAllocation;
import org.sheepy.lily.game.api.audio.IAudioHandle;
import org.sheepy.vsand.model.Material;

public final class MaterialSoundManager
{
	private static final AudioConfiguration audioConfig = new AudioConfiguration.Builder().gain(0.85f).build();

	private IAudioHandle audioHandle;

	public void start(Material material)
	{
		final var paintSound = material.getPaintSound();
		if (paintSound != null)
		{
			final var pitch = material.getPitch();
			final var soundAdapter = paintSound.adapt(IAudioAllocation.class);
			audioHandle = soundAdapter.play(audioConfig.builder().pitch(pitch).build());
		}
	}

	public void stop()
	{
		if (audioHandle != null)
		{
			audioHandle.end();
			audioHandle = null;
		}
	}
}
