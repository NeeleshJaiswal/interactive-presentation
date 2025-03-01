// Please, do not change this file.

const apiUrl = `${Cypress.env("apiUrl")}`

describe('Backend Test Spec', () => {

  it('should call ping', () => {
    cy.request({
      failOnStatusCode: false,
      method: 'GET',
      url: `${apiUrl}/ping`,
    }).then((response) => {
      assert.equal(response.status, 200, "The application should respond with 200 on ping request")
    })
  })

  it('creating a presentation with invalid data should result with 400 status code', () => {
    cy.request({
      failOnStatusCode: false,
      method: 'POST',
      url: `${apiUrl}/presentations`,
      headers: {
        "Content-Type": "application/json"
      },
      body: {}
    }).then((response) => {
      assert.equal(response.status, 400, "Response status should be 400 when creating a presentation with no polls")
    })
  })

  it('reading a presentation using an unknown presentation id should result with 404 status code', () => {
    cy.request({
      failOnStatusCode: false,
      method: 'GET',
      url: `${apiUrl}/presentations/unknown_presentation_id/polls/current`,
      headers: {
        "Content-Type": "application/json"
      }
    }).then((response) => {
      assert.equal(response.status, 404, 'A 404 status code should be returned in case of presentation of given id not found')
    })
  })

  it('creating a presentation, and showing a poll', () => {
    let presentationId, pollId
    cy.request({
      failOnStatusCode: false,
      method: 'POST',
      url: `${apiUrl}/presentations`,
      headers: {
        "Content-Type": "application/json"
      },
      body: {
        polls: [
          {
            question: "What's your favorite pet?",
            options: [
              {key: "A", value: "Dog"},
              {key: "B", value: "Cat"},
              {key: "C", value: "Crocodile"}
            ]
          },
          {
            question: "Which of the countries would you like to visit the most?",
            options: [
              {key: "A", value: "Argentina"},
              {key: "B", value: "Austria"},
              {key: "C", value: "Australia"}
            ]
          }
        ]
      }
    }).then((response) => {
      assert.equal(response.status, 201, "Response status is 201 when creating a presentation")
      assert.isDefined(response.body.presentation_id, "Presentation Id is present when creating a presentation")

      presentationId = response.body.presentation_id

      cy.request({
        failOnStatusCode: false,
        method: 'PUT',
        url: `${apiUrl}/presentations/${presentationId}/polls/current`,
        headers: {
          "Content-Type": "application/json"
        }
      }).then((response) => {
        assert.equal(response.status, 200, 'switching to the next poll must return 200 http code')
        assert.isDefined(response.body.poll_id, 'poll_id should be returned')
        assert.equal(response.body.question, "Which of the countries would you like to visit the most?")
        assert.isUndefined(response.body.votes, 'votes should not be returned')

        pollId = response.body.poll_id

        cy.request({
          failOnStatusCode: false,
          method: 'GET',
          url: `${apiUrl}/presentations/${presentationId}/polls/current`,
          headers: {
            "Content-Type": "application/json"
          }
        }).then((response) => {
          assert.equal(response.status, 200, 'reading presentation as voter should return 200')
          assert.isDefined(response.body.poll_id, 'poll_id is included in the body')
          assert.equal(response.body.question, "Which of the countries would you like to visit the most?")
          assert.isUndefined(response.body.votes, 'votes are not returned')
        })
      })
    })
  })
})
