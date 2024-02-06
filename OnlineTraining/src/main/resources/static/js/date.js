document.addEventListener('DOMContentLoaded', function () {
   let startDateInput = document.getElementById('startDate');
   let endDateInput = document.getElementById('endDate');

   startDateInput.addEventListener('change', function () {
       let startDateValue = startDateInput.value;
       let minEndDate = new Date(startDateValue);
       minEndDate.setDate(minEndDate.getDate() + 30);

       let formattedMinEndDate = minEndDate.toISOString().split('T')[0];
       endDateInput.min = formattedMinEndDate;
   });
});