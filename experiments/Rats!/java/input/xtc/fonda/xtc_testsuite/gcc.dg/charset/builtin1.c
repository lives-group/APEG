/* isdigit(c) can be optimized to ((unsigned)c) - '0' <= 9, but only if
   we know the correct value of '0'.  PR 18785.  */

// {{ dg-checkwhat "c-analyzer" }}

extern int isdigit(int);
extern void abort(void);

static int str1(void) { return '1'; }
static int strA(void) { return 'A'; }

int
main(void)
{
  if (!isdigit('1'))
    abort();
  if (isdigit('A'))
    abort();
  if (!isdigit(str1()))
    abort();
  if (isdigit(strA()))
    abort();
  return 0;
}
