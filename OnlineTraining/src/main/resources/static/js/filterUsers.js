function filterUsers() {
    let roleFilter = $("#roleFilter").val();

    $.ajax({
        type: "GET",
        url: "/admins/users",
        data: {
            roleFilter: roleFilter
        },
        success: function(data) {
            $('#usersTable').replaceWith($(data).find('#usersTable'));
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
}
