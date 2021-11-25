/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH under
 * one or more contributor license agreements. See the NOTICE file distributed
 * with this work for additional information regarding copyright ownership.
 * Licensed under the Zeebe Community License 1.1. You may not use this file
 * except in compliance with the Zeebe Community License 1.1.
 */
package io.camunda.zeebe.dmn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.jupiter.api.Test;

class DmnParsingTest {

  private final DecisionEngine decisionEngine = DecisionEngineFactory.createDecisionEngine();

  @Test
  void shouldParseDecisionTable() {
    // given
    final var inputStream = getClass().getResourceAsStream("/decision-table.dmn");
    assertThat(inputStream).isNotNull();

    // when
    final var parseResult = decisionEngine.parse(inputStream);

    // then
    assertThat(parseResult.isRight())
        .describedAs("Expect that the DMN is parsed successfully")
        .isTrue();

    final ParsedDecisions parsedDecisions = parseResult.get();
    assertThat(parsedDecisions.getDecisionRequirementsId()).isEqualTo("force-users");
    assertThat(parsedDecisions.getDecisionRequirementsName()).isEqualTo("Force Users");
    assertThat(parsedDecisions.getDecisionRequirementsNamespace())
        .isEqualTo("http://camunda.org/schema/1.0/dmn");

    assertThat(parsedDecisions.getDecisions())
        .hasSize(1)
        .extracting(ParsedDecision::getDecisionId, ParsedDecision::getDecisionName)
        .contains(tuple("jediOrSith", "Jedi or Sith"));
  }
}
