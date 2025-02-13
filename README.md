# Interactive Presentation Backend - Level 1

Below, you'll find the instructions for getting started with your task. Please read them carefully to avoid unexpected issues. Best of luck!

## Time estimate

Between 2 and 3 hours, plus the time to set up the codebase.

## Mandatory steps before you get started

1. You should already have your project setup from the coding test start page but if not check out [this guide here](https://help.alvalabs.io/en/articles/9028914-how-to-set-up-the-codebase-for-your-coding-test) for more information.
2. Learn [how to get help](https://help.alvalabs.io/en/articles/9028899-how-to-ask-for-help-with-coding-tests) if you run into an issue with your coding test.


## The task

<!--TASK_INSTRUCTIONS_START-->

## System Description

There's a presenter on the stage. They ask the audience a series of questions. One poll per each slide. The people in the audience can vote as long as the current question is displayed.

<img width="550" src="https://user-images.githubusercontent.com/1162212/139849812-de799423-efc1-42f4-8298-e779c3aa17d7.png" />

## Sequence Diagram

The full system flows are visualised by the following [sequence diagram](https://swimlanes.io/u/mmSDwCQdM).


## What you should build

Your task is to build a backend app that fulfills **several endpoints of the [Interactive Presentation API](https://infra.devskills.app/interactive-presentation/api/4.0.0)** and **make the provided API tests pass**.

Please **only implement the following endpoints**:

1. `GET /ping`
2. `POST /presentations`
3. `PUT /presentations/{presentation_id}/polls/current`
4. `GET /presentations/{presentation_id}/polls/current`

<!--TASK_INSTRUCTIONS_END-->
### Solution expectations

- Do your best to make the [provided E2E tests](cypress/e2e/backend.cy.js) pass. Check out [this tutorial](https://help.alvalabs.io/en/articles/9028831-how-to-work-with-cypress) to learn how to execute these tests and analyze the results.

## When you are done

1. [Create a new Pull Request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request) from the branch where you've committed your solution to the default branch of this repository. **Please do not merge the created Pull Request**.
2. Go to your application in [Alva Labs](https://app.alvalabs.io) and submit your test.

---

Authored by [Alva Labs](https://www.alvalabs.io/).
