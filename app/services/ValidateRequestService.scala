/*
 * Copyright 2021 HM Revenue & Customs
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

package services

import javax.inject.Inject
import models.ErrorModel
import play.api.libs.json.JsValue
import play.api.mvc.Results.Status
import play.api.mvc.{Request, Result}
import utils.JsonValidation

import scala.concurrent.ExecutionContext

class ValidateRequestService @Inject()() extends JsonValidation {

  def validateRequest(error: ErrorModel, APINumber: Int)(implicit request: Request[JsValue], ec: ExecutionContext): Either[Result, Boolean] = {

    val schemaFile = APINumber match {
      case 1390 => "1390_CreateUpdateIncomeSourceSchema.json"
      case 1393 => "1393_CreateIncomeSourceSchema.json"
    }

    if (isValidJsonAccordingToJsonSchema(request.body, s"/jsonSchemas/$schemaFile")) {
      Right(true)
    } else {
      Left(Status(error.status)(error.toJson))
    }
  }

}