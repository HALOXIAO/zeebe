/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Zeebe Community License 1.1. You may not use this file
 * except in compliance with the Zeebe Community License 1.1.
 */
package io.camunda.zeebe.dmn.impl;

import io.camunda.zeebe.dmn.DecisionEngine;
import io.camunda.zeebe.dmn.ParseFailure;
import io.camunda.zeebe.dmn.ParsedDecisions;
import io.camunda.zeebe.util.Either;
import java.io.InputStream;
import org.camunda.dmn.DmnEngine;

public final class DmnScalaDecisionEngine implements DecisionEngine {

  private final DmnEngine dmnEngine;

  public DmnScalaDecisionEngine() {
    dmnEngine = new DmnEngine.Builder().build();
  }

  @Override
  public Either<ParseFailure, ParsedDecisions> parse(final InputStream stream) {
    final var parseResult = dmnEngine.parse(stream);

    if (parseResult.isLeft()) {
      final DmnEngine.Failure failure = parseResult.left().get();
      final var failureMessage = failure.message();

      return Either.left(new ParseFailureMessage(failureMessage));

    } else {
      final var parsedDmn = parseResult.right().get();

      return Either.right(ParsedDmnScalaDecisions.of(parsedDmn));
    }
  }
}
