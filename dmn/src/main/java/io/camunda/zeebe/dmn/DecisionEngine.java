/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Zeebe Community License 1.1. You may not use this file
 * except in compliance with the Zeebe Community License 1.1.
 */
package io.camunda.zeebe.dmn;

import io.camunda.zeebe.util.Either;
import java.io.InputStream;

public interface DecisionEngine {

  Either<ParseFailure, ParsedDecisions> parse(InputStream inputStream);
}
