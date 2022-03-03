package com.smalaca.taskmanager.architecturetests;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

class QueryStructureTest {
    @Test
    void commandsShouldDependOnlyOnCommand() {
        JavaClasses javaClasses = ProjectManagementClasses.getAll();

        ArchRuleDefinition.classes()
                .that().resideInAPackage("..query..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                        "..query..", "..repository..", "..model.entities..", "java..")

                .because("2022-03-03-cqrs.md")
                .check(javaClasses);
    }
}
