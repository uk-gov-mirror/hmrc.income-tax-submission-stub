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

package mocks

import models.DataModel
import org.scalamock.handlers.{CallHandler1, CallHandler2}
import org.scalamock.scalatest.MockFactory
import play.api.libs.json.Json.JsValueWrapper
import reactivemongo.api.commands.{UpdateWriteResult, WriteError, WriteResult}
import repositories.DataRepository
import testUtils.TestSupport

import scala.concurrent.{ExecutionContext, Future}

trait MockDataRepository extends TestSupport with MockFactory {

  val successWriteResult = UpdateWriteResult(ok = true, n = 1, nModified = 0, upserted = Seq(), writeErrors = Seq(), None, None, None)
  val errorWriteResult = UpdateWriteResult(ok = false, n = 1, nModified = 0, upserted = Seq(), writeErrors = Seq(WriteError(1,1,"Error")), None, None, None)

  lazy val mockDataRepository: DataRepository = mock[DataRepository]

  def mockAddEntry(document: DataModel)
                  (response: Future[WriteResult]): CallHandler2[DataModel, ExecutionContext, Future[WriteResult]] = {
    (mockDataRepository.addEntry(_: DataModel)(_: ExecutionContext))
      .expects(document, *)
      .returning(response)
  }

  def mockRemoveById(url: String)
                    (response: Future[WriteResult]): CallHandler2[String, ExecutionContext, Future[WriteResult]] = {
    (mockDataRepository.removeById(_: String)(_: ExecutionContext))
      .expects(url, *)
      .returning(response)
  }

  def mockRemoveAll()
                   (response: Future[WriteResult]): CallHandler1[ExecutionContext, Future[WriteResult]] = {
    (mockDataRepository.removeAll()(_: ExecutionContext))
      .expects(*)
      .returning(response)
  }

  def mockFindById(url: String)
                  (response: Future[DataModel]): CallHandler2[String, ExecutionContext, Future[DataModel]] = {
    (mockDataRepository.findById(_: String)(_: ExecutionContext))
      .expects(*, *)
      .returning(response)
  }

  def mockFind(response: Future[Option[DataModel]]): CallHandler2[(String, JsValueWrapper), ExecutionContext, Future[Option[DataModel]]] = {
    (mockDataRepository.find(_: (String, JsValueWrapper))(_: ExecutionContext))
      .expects(*, *)
      .returning(response)
  }
}
