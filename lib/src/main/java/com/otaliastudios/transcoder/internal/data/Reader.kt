package com.otaliastudios.transcoder.internal.data

import com.otaliastudios.transcoder.common.TrackType
import com.otaliastudios.transcoder.internal.pipeline.BaseStep
import com.otaliastudios.transcoder.internal.pipeline.Channel
import com.otaliastudios.transcoder.internal.pipeline.State
import com.otaliastudios.transcoder.internal.utils.Logger
import com.otaliastudios.transcoder.source.DataSource
import java.nio.ByteBuffer


internal data class ReaderData(val chunk: DataSource.Chunk, val id: Int)

internal interface ReaderChannel : Channel {
    fun buffer(): Pair<ByteBuffer, Int>?
}

internal class Reader(
        private val source: DataSource,
        private val track: TrackType
) : BaseStep<Unit, Channel, ReaderData, ReaderChannel>() {

    private val log = Logger("Reader")
    override val channel = Channel
    private val chunk = DataSource.Chunk()

    private inline fun nextBufferOrWait(action: (ByteBuffer, Int) -> State<ReaderData>): State<ReaderData> {
        val buffer = next.buffer()
        if (buffer == null) {
            log.v("Returning State.Wait because buffer is null.")
            return State.Wait
        } else {
            return action(buffer.first, buffer.second)
        }
    }

    override fun step(state: State.Ok<Unit>, fresh: Boolean): State<ReaderData> {
        return if (source.isDrained) {
            nextBufferOrWait { byteBuffer, id ->
                // TODO we might have to clear the chunk fields
                chunk.buffer = byteBuffer
                State.Eos(ReaderData(chunk, id))
            }
        } else if (!source.canReadTrack(track)) {
            log.i("Returning State.Wait because source can't read this track right now.")
            State.Wait
        } else {
            nextBufferOrWait { byteBuffer, id ->
                chunk.buffer = byteBuffer
                source.readTrack(chunk)
                State.Ok(ReaderData(chunk, id))
            }
        }
    }
}