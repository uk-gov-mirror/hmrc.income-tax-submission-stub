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

package repositories

import models._
import play.api.libs.json.Format
import reactivemongo.api.DB
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID
import uk.gov.hmrc.mongo.ReactiveRepository
import play.api.libs.json.Writes.StringWrites
import play.api.libs.json.Reads.StringReads
import scala.concurrent.{ExecutionContext, Future}

trait DynamicStubRepository[T, O] extends ReactiveRepository[T, BSONObjectID] {

  def findById(o: O)(implicit ec: ExecutionContext): Future[T]

  def removeById(o: O)(implicit ec: ExecutionContext): Future[WriteResult]

  def removeAll()(implicit ec: ExecutionContext): Future[WriteResult]

  def addEntry(t: T)(implicit ec: ExecutionContext): Future[WriteResult]

}

class DataRepositoryBase(implicit mongo: () => DB, formats: Format[DataModel])
  extends ReactiveRepository[DataModel, String]("data", mongo, formats, Format(StringReads, StringWrites))
