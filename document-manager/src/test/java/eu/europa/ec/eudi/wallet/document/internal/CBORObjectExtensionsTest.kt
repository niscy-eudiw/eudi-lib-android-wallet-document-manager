/*
 * Copyright (c) 2024 European Commission
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.europa.ec.eudi.wallet.document.internal

import com.upokecenter.cbor.CBORObject
import kotlinx.datetime.LocalDate
import java.time.ZonedDateTime
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CBORObjectExtensionsTest {

    @Test
    fun `getEmbeddedCBORObject with Tag 24 returns Decoded Object`() {
        val byteArray =
            CBORObject.FromObjectAndTag(CBORObject.FromObject("test").EncodeToBytes(), 24)
                .EncodeToBytes()
        val result = byteArray.getEmbeddedCBORObject()

        assertEquals("test", result.AsString())
    }

    @Test
    fun `getEmbeddedCBORObject without Tag 24 returns Same Object`() {
        val embedded = CBORObject.FromObject("test").EncodeToBytes()
        val byteArray = CBORObject.FromObject(embedded).EncodeToBytes()
        val result = byteArray.getEmbeddedCBORObject()

        assertContentEquals(embedded, result.GetByteString())
    }

    @Test
    fun `withTag24 returns ByteArray with Tag 24`() {
        val byteArray = "test".toByteArray()
        val result = byteArray.withTag24()

        val cborObject = CBORObject.DecodeFromBytes(result)
        assertTrue(cborObject.HasTag(24))
    }

    @Test
    fun `toObject with Null returns Null`() {
        val byteArray = CBORObject.Null.EncodeToBytes()
        val result = byteArray.toObject()

        assertNull(result)
    }

    @Test
    fun `toObject with Boolean True returns True`() {
        val byteArray = CBORObject.True.EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(true, result)
    }

    @Test
    fun `toObject with Boolean False returns False`() {
        val byteArray = CBORObject.False.EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(false, result)
    }

    @Test
    fun `toObject with Integer returns Integer`() {
        val byteArray = CBORObject.FromObject(42).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(42, result)
    }

    @Test
    fun `toObject with Long returns Long`() {
        val long = Random.nextLong()
        val byteArray = CBORObject.FromObject(long).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(long, result)
    }

    @Test
    fun `toObject with Double returns Double`() {
        val double = Random.nextDouble(from = Float.MAX_VALUE.toDouble(), until = Double.MAX_VALUE)
        val byteArray = CBORObject.FromObject(double).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(double, result)
    }

    @Test
    fun `toObject with Float returns Double`() {
        val float = Random.nextFloat()
        val byteArray = CBORObject.FromObject(float).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(float.toDouble(), result)
    }

    @Test
    fun `toObject with Text String returns String`() {
        val byteArray = CBORObject.FromObject("test").EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals("test", result)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun `toObject with Byte String returns ByteArray`() {
        val byteArray = CBORObject.FromObject("test".toByteArray()).EncodeToBytes()
        val result = byteArray.toObject()

        assertIs<ByteArray>(result)
        assertContentEquals("test".toByteArray(), result)
    }

    @Test
    fun `toObject with Array returns List`() {
        val byteArray = CBORObject.NewArray().Add(1).Add(2).Add(3).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(listOf(1, 2, 3), result)
    }

    @Test
    fun `toObject with Map returns Map`() {
        val byteArray = CBORObject.NewMap().Add("key", "value").EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(mapOf("key" to "value"), result)
    }

    @Test
    fun `toObject with Nested Structures returns Correctly Parsed Object`() {
        val byteArray = CBORObject.NewMap().apply {
            Add("key1", CBORObject.NewArray().Add(1).Add(2).Add(3))
            Add("key2", CBORObject.NewMap().Add("nestedKey", "nestedValue"))
        }.EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(
            mapOf(
                "key1" to listOf(1, 2, 3),
                "key2" to mapOf("nestedKey" to "nestedValue")
            ), result
        )
    }

    @Test
    fun `toObject with datetime string and tag0 returns ZonedDateTime`() {
        val dateTimeStr = "2024-01-01T00:00:00Z"
        val dateTime = ZonedDateTime.parse(dateTimeStr)
        val byteArray = CBORObject.FromObjectAndTag(dateTimeStr, 0).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(dateTime, result)
    }

    @Test
    fun `toObject with datetime string without tag0 returns string`() {
        val dateTimeStr = "2024-01-01T00:00:00Z"
        val byteArray = CBORObject.FromObject(dateTimeStr).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(dateTimeStr, result)
    }

    @Test
    fun `toObject with invalid datetime string and tag0 returns string`() {
        val dateTimeStr = "invalid"
        val byteArray = CBORObject.FromObjectAndTag(dateTimeStr, 0).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(dateTimeStr, result)
    }

    @Test
    fun `toObject with date string and tag1004 returns LocalDate`() {
        val dateStr = "2024-01-01"
        val date = LocalDate.parse(dateStr)
        val byteArray = CBORObject.FromObjectAndTag(dateStr, 1004).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(date, result)
    }

    @Test
    fun `toObject with date string without tag1004 returns string`() {
        val dateStr = "2024-01-01"
        val byteArray = CBORObject.FromObject(dateStr).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(dateStr, result)
    }

    @Test
    fun `toObject with invalid date string and tag1004 returns string`() {
        val dateStr = "invalid"
        val byteArray = CBORObject.FromObjectAndTag(dateStr, 1004).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals(dateStr, result)
    }

    @Test
    fun `toObject with bytearray and tag24 returns nested cbor object`() {
        val nestedCbor = CBORObject.FromObject("test")
        val byteArray = CBORObject.FromObjectAndTag(nestedCbor.EncodeToBytes(), 24).EncodeToBytes()
        val result = byteArray.toObject()

        assertEquals("test", result)
    }

    @Test
    fun `toDigestIdMapping with Valid Data returns Correct Mapping`() {
        val cbor = CBORObject.NewMap().apply {
            Add("namespace1", CBORObject.NewArray().Add(CBORObject.NewMap().apply {
                Add("elementIdentifier", CBORObject.FromObject("id1"))
                Add("elementValue", CBORObject.FromObject("value1"))
            }))
        }

        val result = cbor.toDigestIdMapping()

        assertEquals(1, result.size)
        assertEquals(1, result["namespace1"]!!.size)
    }

    @Test
    fun `toDigestIdMapping with Empty Map returns Empty Mapping`() {
        val cbor = CBORObject.NewMap()

        val result = cbor.toDigestIdMapping()

        assertEquals(0, result.size)
    }

    @Test
    fun `asNameSpacedData with Valid Data returns Correct NameSpacedData`() {
        val cbor = CBORObject.NewMap().apply {
            Add("namespace1", CBORObject.NewArray().Add(CBORObject.NewMap().apply {
                Add("elementIdentifier", CBORObject.FromObject("id1"))
                Add("elementValue", CBORObject.FromObject("value1"))
            }))
        }

        val result = cbor.asNameSpacedData()

        assertEquals(
            "value1",
            CBORObject.DecodeFromBytes(result.getDataElement("namespace1", "id1")).AsString()
        )
    }

    @Test
    fun `asNameSpacedData with Empty Map returns Empty NameSpacedData`() {
        val cbor = CBORObject.NewMap()

        val result = cbor.asNameSpacedData()

        assertTrue(result.nameSpaceNames.isEmpty())
    }
}