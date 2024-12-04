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

package eu.europa.ec.eudi.wallet.document.format

import eu.europa.ec.eudi.wallet.document.ElementIdentifier
import eu.europa.ec.eudi.wallet.document.NameSpace

/**
 * A document claim that is part of a MsoMdoc document.
 * @property nameSpace The namespace of the claim.
 * @property elementIdentifier The identifier of the claim.
 * @property elementValue The value of the claim.
 * @property identifier The identifier of the claim.
 * @property value The value of the claim.
 */
data class MsoMdocClaim(
    val nameSpace: NameSpace,
    val elementIdentifier: ElementIdentifier,
    val elementValue: Any?
) : DocumentClaim(elementIdentifier, elementValue)