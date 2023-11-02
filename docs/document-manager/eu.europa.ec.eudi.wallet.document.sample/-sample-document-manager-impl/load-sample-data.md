//[document-manager](../../../index.md)/[eu.europa.ec.eudi.wallet.document.sample](../index.md)/[SampleDocumentManagerImpl](index.md)/[loadSampleData](load-sample-data.md)

# loadSampleData

[androidJvm]\
open override fun [loadSampleData](load-sample-data.md)(sampleData: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)): [LoadSampleResult](../-load-sample-result/index.md)

Loads the sample data into the document manager.

The sample data is a CBOR bytearray that has the following structure:

```cddl
Data = {
 "documents" : [+Document], ; Returned documents
}
Document = {
 "docType" : DocType, ; Document type returned
 "issuerSigned" : IssuerSigned, ; Returned data elements signed by the issuer
}
IssuerSigned = {
 "nameSpaces" : IssuerNameSpaces, ; Returned data elements
}
IssuerNameSpaces = { ; Returned data elements for each namespace
 + NameSpace => [ + IssuerSignedItemBytes ]
}
IssuerSignedItem = {
 "digestID" : uint, ; Digest ID for issuer data authentication
 "random" : bstr, ; Random value for issuer data authentication
 "elementIdentifier" : DataElementIdentifier, ; Data element identifier
 "elementValue" : DataElementValue ; Data element value
}
```

#### Return

[LoadSampleResult.Success](../-load-sample-result/-success/index.md) if the sample data has been loaded successfully. Otherwise, returns [LoadSampleResult.Error](../-load-sample-result/-error/index.md), with the error message.

#### Parameters

androidJvm

| |
|---|
| sampleData |