function filterCoaches() {
    let genderFilter = $("#genderFilter").val();
    let experienceFilter = $("#experienceFilter").val();
    let ageFilter = $("#ageFilter").val();
    let educationFilter = $("#educationFilter").val();
    let monthlyPriceFilter = $("#monthlyPriceFilter").val();


    $.ajax({
        type: "GET",
        url: "/clients/client-page",
        data: {
            gender: genderFilter,
            experience: experienceFilter,
            age: ageFilter,
            education: educationFilter,
            monthlyPrice: monthlyPriceFilter
        },
        success: function(data) {
            $('#coachesTable').replaceWith($(data).find('#coachesTable'));
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
}
