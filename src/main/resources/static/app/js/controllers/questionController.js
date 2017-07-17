'use strict';

app.controller("questionController", ['$scope', '$location', 'questionService', 'questionData', 'skillService', 'publisherService', 'topicService', 'subjectService', 'sectionService', 'filterFilter', 'questionAnswerService', 'Upload', '$timeout', 'Notification', function ($scope, $location, questionService, questionData, skillService, publisherService, topicService, subjectService, sectionService, filterFilter, questionAnswerService, Upload, $timeout, Notification) {

        $scope.status;
        findAllQuestions();
        ///findAllTopics();
        //findAllSkills();
        findAllPublishers();
        findAllSubjects();
        // findAllSections();
        $scope.questionData = questionData;




        /* question answers */


        $scope.questionAnswer = [
            {
                description: '',
                correct: '',
                point: '1',
                option: 'Option A',
                image: ''
            }



        ];



        console.log("QA Array " + $scope.questionAnswer.length);

        function add(itemToAdd) {
            console.log("Add Called...." + itemToAdd);
            // var index = $scope.questionAnswer.indexOf(itemToAdd);
            var index = $scope.questionAnswer.length - 1;
            console.log("Index is " + index);
            $scope.questionAnswer.splice(index, 1, itemToAdd);

            // $scope.questionAnswer.push(angular.copy(itemToAdd))
        }

        function addNew(optionLetter) {
            console.log("Addnew function called  with optionletter  " + optionLetter);
            $scope.questionAnswer.push({
                description: '',
                correct: '',
                point: '1',
                option: 'Option ' + optionLetter,
                image: ''
            });
            console.log("qaToAdd size " + $scope.questionAnswer.length)

            add({
                description: '',
                correct: '',
                point: 1,
                option: 'Option ' + optionLetter,
                image: ''
            });
        }
        addNew('B');
        addNew('C');
        addNew('D');


        /* end qas */

        function findAllQuestions() {
            questionService.findAllQuestions().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.questions = results.data;
                    $scope.pageSize = 5;
                    $scope.$watch('search.description', function (term) {
                        var obj = {description: term};

                        $scope.filterList = filterFilter($scope.questions, obj);
                        $scope.currentPage = 1;
                    });
                } else {
                    $scope.questions = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load questions data: ' + error.message;
                    });
        }
        ;

        function findAllSkills() {
            skillService.findAllSkills().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.skills = results.data;
                } else {
                    $scope.skills = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load skills data: ' + error.message;
                    });
        }
        ;

        function findAllPublishers() {
            publisherService.findAllPublishers().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.publishers = results.data;
                } else {
                    $scope.publishers = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load publishers data: ' + error.message;
                    });
        }
        ;

        function findAllTopics() {
            topicService.findAllTopics().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.topics = results.data;
                } else {
                    $scope.topics = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load topics data: ' + error.message;
                    });
        }
        ;

        function findAllSubjects() {
            subjectService.findAllSubjects().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.subjects = results.data;
                } else {
                    $scope.subjects = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load subjects data: ' + error.message;
                    });
        }
        ;


        function findAllSections() {
            sectionService.findAllSections().then(function (results) {
                console.log(results.data._embedded);
                if (results.data != undefined && results.data.length > 0) {
                    $scope.sections = results.data;
                } else {
                    $scope.sections = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load sections data: ' + error.message;
                    });
        }
        ;

        $scope.getSubjectSections = function () {
            $scope.sections = [];
            questionService.getSubjectSections($scope.question.subject.id).then(
                    function (result) {
                        if (result.data != undefined && result.data.length > 0) {

                            // console.log(result);
                            $scope.sections = result.data;
                        } else {
                            $scope.sections = [];
                        }
                    },
                    function (error) {
                        $scope.status = 'Unable to load Sections data: ' + error.message;
                    }
            );


        };


        $scope.getSectionTopics = function () {
            $scope.topics = [];
            $scope.topics = questionService.getSectionTopics($scope.question.section.id).then(function (result)

            {
                if (result.data != undefined && result.data.length > 0) {

                    // console.log(result);
                    $scope.topics = result.data;
                } else {
                    $scope.topics = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load Sections data: ' + error.message;


                    });
        }


        $scope.getTopicSkills = function () {
            $scope.skills = [];
            $scope.skills = questionService.getTopicSkills($scope.question.topic.id).then(function (result)

            {
                if (result.data != undefined && result.data.length > 0) {

                    // console.log(result);
                    $scope.skills = result.data;
                } else {
                    $scope.skills = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load Sections data: ' + error.message;


                    });
        }

        $scope.findQuestionById = function (id) {
            questionService.findQuestionById(id).then(function (results) {
                $scope.questionData.currQuestion = results.data;
                $location.path('/view-question');
            });
        };

        $scope.updateQuestionClicked = function (newquestion) {
            $scope.questionData.currQuestion = newquestion;
            $location.path('/update-question');
        };


        /* IMAGE Upload */


        // for multiple files:
        //    uploadFiles(questionImage,theQuestion,results.data,questionAnswerImages);

        var uploadFiles = function (image, question, qas, qasImages) {
            console.log(" THe Received  question :" + question);
            //var qas= $scope.question.questionAnswer;
            var qfiles, qafiles;
            qafiles = [];

            console.log("Question Image : " + image);
            //console.log(qas);



            if (typeof question !== 'undefined' && typeof image !== 'undefined') {


                Upload.upload({url: 'media/upload', data: {image: image, questionId: question.id}}).then(function (resp) {
                    console.log('Success ' + resp.config.data + 'uploaded. Response: ' + resp.data);
                }, function (resp) {
                    console.log('Error status: ' + resp.status);
                });

            }

            angular.forEach(qasImages, function (value, index) {
                value.question = null;


            });
            console.log("Submitted question options")
            console.log(JSON.stringify(qas));
            console.log("Uploaded Images");
            console.log(JSON.stringify(qasImages));
            if (typeof qas !== 'undefined' && qasImages && qasImages.length) {
                // console.log(JSON.stringify(qasImages));
                //console.log(JSON.stringify(qas));

                for (var i = 0; i < qasImages.length; i++) {
                    Upload.upload({url: 'media/upload/answer', data: {image: qasImages[i].image, questionAnswer: qas[i].id}}).then(function (resp) {
                        console.log('Success ' + resp.config.data + 'uploaded. Response: ' + resp.data);
                    }, function (resp) {
                        console.log('Error status: ' + resp.status);
                    });
                }


            }
        }



        /* End Image Upload*/

        $scope.addQuestion = function (question) {
            console.log(question);
            var questionAnswer = question.questionAnswer;
            question.questionAnswer = null;


            var questionImage = question.image;
            question.image = null;

            var questionAnswerImages = [];
            var theQuestion = '';
            var questionAnswers = [];

            questionService.addQuestion(question).then(function (results) {
                console.log(results);
                theQuestion = results.data;
                angular.forEach(questionAnswer, function (value, index) {

                    value.question = {id: results.data.id};
                    theQuestion = results.data;

                    questionAnswerImages.push({image: value.image, question: results.data});
                    value.image = null;

                    //value.question.id= results.data.id;
                    questionAnswers.push(value);
                });

                console.log(questionAnswers);

                questionAnswerService.addQuestionAnswer(questionAnswers).then(function (results) {
                    console.log(" THe Senting question :" + theQuestion);
                    uploadFiles(questionImage, theQuestion, results.data, questionAnswerImages);

                    // console.log(questionAnswers);

                    //$location.path('/questionanswers-list');


                });

                Notification.success({message: 'Question added successfully', delay: 5000});


                // console.log(questionAnswer);
                // console.log(question);

                // $location.path('/questions-list');
            },
                    function (error) {
                        $scope.status = 'Unable to add question: ' + error.message;
                    });
        };

        $scope.addQuestionAnswer = function (questionAnswer) {
            questionAnswerService.addQuestionAnswer(questionAnswer).then(function (results) {
                console.log(questionAnswer);
                $location.path('/questionanswers-list');
            },
                    function (error) {
                        $scope.status = 'Unable to add question: ' + error.message;
                    });
        };

        $scope.deleteQuestion = function (id) {
            var c = confirm("Are you sure?");
            if (c == true) {
                questionService.deleteQuestion(id).then(function (results) {
                    findAllQuestions();
                    Notification.success({message: 'Question deleted successfully', delay: 5000});
                }, function (error) {
                    $scope.status = 'Unable to delete question: ' + error.message;
                });
            } else {
                return;
            }
        };




        //Trix Text Editor

        var events = ['trixInitialize', 'trixChange', 'trixSelectionChange', 'trixFocus', 'trixBlur', 'trixFileAccept', 'trixAttachmentAdd', 'trixAttachmentRemove'];

        for (var i = 0; i < events.length; i++) {
            $scope[events[i]] = function (e) {
                console.info('Event type:', e.type);
            };
        }
        ;




    }])

        .filter('start', function () {
            return function (input, start) {
                if (!input || !input.length) {
                    return;
                }

                start = +start;
                return input.slice(start);
            };
        })



        //Strict contextual editor filter

        .filter('trusted', ['$sce', function ($sce) {
                var div = document.createElement('div');
                return function (text) {
                    div.innerHTML = text;
                    return $sce.trustAsHtml(div.textContent);
                };
            }]);

app.controller("editQuestionController", ['$scope', '$location', 'questionService', 'questionData', 'Notification', function ($scope, $location, questionService, questionData, Notification) {

        $scope.questionData = questionData;

        $scope.updateQuestion = function (updatedQuestion) {
            questionService.updateQuestion(updatedQuestion).then(function (results) {
                $location.path('/questions-list');
                Notification.success({message: 'Question update successfull', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to update question: ' + error.message;
                    });
        };
    }]);
