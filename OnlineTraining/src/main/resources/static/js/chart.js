   document.addEventListener('DOMContentLoaded', function () {
        let ctx = document.getElementById('bodyWeightChart').getContext('2d');

        if (measurements) {
            var bodyWeightData = measurements.map(function (measurement, index) {
                return {
                    x: index + 1,
                    y: measurement.bodyWeight
                };
            });

            let bodyFatData = measurements.map(function (measurement, index) {
                return {
                    x: index + 1,
                    y: measurement.bodyFat
                };
            });

            let waistCircumferenceData = measurements.map(function (measurement, index) {
                return {
                    x: index + 1,
                    y: measurement.waistCircumference
                };
            });

            let chestCircumferenceData = measurements.map(function (measurement, index) {
                return {
                    x: index + 1,
                    y: measurement.chestCircumference
                };
            });

            let armCircumferenceData = measurements.map(function (measurement, index) {
                return {
                    x: index + 1,
                    y: measurement.armCircumference
                };
            });

            let legCircumferenceData = measurements.map(function (measurement, index) {
                return {
                    x: index + 1,
                    y: measurement.legCircumference
                };
            });

            let bodyWeightChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    datasets: [
                        {
                            label: 'Body Weight',
                            data: bodyWeightData,
                            backgroundColor: '#33cc77',
                            borderWidth: 2
                        },
                        {
                            label: 'Body Fat',
                            data: bodyFatData,
                            backgroundColor: '#6fa8dc',
                            borderWidth: 2
                        },
                        {
                            label: 'Waist Circumference',
                            data: waistCircumferenceData,
                            backgroundColor: '#ffcc00',
                            borderWidth: 2,
                            hidden: true
                        },
                        {
                            label: 'Chest Circumference',
                            data: chestCircumferenceData,
                            backgroundColor: '#ff6666',
                            borderWidth: 2,
                            hidden: true
                        },
                        {
                            label: 'Arm Circumference',
                            data: armCircumferenceData,
                            backgroundColor: '#9966ff',
                            borderWidth: 2,
                            hidden: true
                        },
                        {
                            label: 'Leg Circumference',
                            data: legCircumferenceData,
                            backgroundColor: '#ff9900',
                            borderWidth: 2,
                            hidden: true
                        }
                    ]
                },
                options: {
                    scales: {
                        x: {
                            type: 'linear',
                            position: 'bottom',
                            ticks: {
                                stepSize: 1
                            },
                            title: {
                                display: true,
                                text: 'Measurement'
                            }
                        },
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'kg,kg/cm'
                            }
                        }
                    }
                }
            });
        }
    });
